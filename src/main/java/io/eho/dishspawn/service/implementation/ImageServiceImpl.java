package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.exception.SaveImageNotPossible;
import io.eho.dishspawn.graphics.processing.TheSketch;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.graphics.processing.util.Randomizer;
import io.eho.dishspawn.graphics.processing.util.Transformer;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.ImageService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeIngredientService recipeIngredientService;
    private final VisualService visualService;
    private final ChefService chefService;
    private TheSketch theSketch;    // custom PApplet class (Processing)
    private PImage pImg;            // Processing class

    @Autowired
    public ImageServiceImpl(RecipeIngredientService recipeIngredientService,
                            VisualService visualService,
                            ChefService chefService) {
//        this.theSketch = getTheSketch();
        this.recipeIngredientService = recipeIngredientService;
        this.visualService = visualService;
        this.chefService = chefService;
    }

    @Override
    public String generateImage(Recipe recipe) {
        this.pImg = null;
        // get recipe-ingredients
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);
        // filter recipe-ingredients with visual impact and mass/volume > 0, sort on mass (biggest first)
        List<RecipeIngredient> recipeIngredientsWithVisualImpactSortedOnMass = recipeIngredientList.stream()
                .filter(ri -> ri.isVisualImpact() == true && ri.getMassOrVolume() > 0)
                .sorted(Comparator.comparingDouble(RecipeIngredient::getMassOrVolume).reversed())
                .collect(Collectors.toList());

        // filter recipe-ingredients with visual impact and mass/volume == 0
        List<RecipeIngredient> recipeIngredientsWithVisualImpactNoMass = recipeIngredientList.stream()
                .filter(ri -> ri.isVisualImpact() == true && ri.getMassOrVolume() == 0)
                .collect(Collectors.toList());

        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
            System.out.println("ri mass / volume > 0: " + ri.getMassOrVolume());
        }

        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactNoMass) {
            System.out.println("ri mass / volume == 0: " + ri.getMassOrVolume());
        }

        // get a total of mass / volume
        int totalSize = 0;
        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
            totalSize += ri.getMassOrVolume();
        }
        System.out.println("total mass: " + totalSize);

        // initiate the sketch (the Processing PApplet)
        this.theSketch = getTheSketch();

        // init a list for storing shapes
        List<Shape> shapeList = new ArrayList<>();

        // set total # of shapes
        int totalShapes = Randomizer.getRandomNumberInRange(120, 180); // this can become another dependency on
        // recipe characteristics
        System.out.println("total shapes: " + totalShapes);

        // for riMass-list, loop
        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
            double riSize = ri.getMassOrVolume();
            System.out.println("name: " + ri.getIngredient().getName() + "; Color hex: " + ri.getColor() + "; Mass: " + riSize);

            // set shape per ri

            int nrOfShapes = (int) Math.ceil((riSize / totalSize) * totalShapes);
            System.out.println("nr of shapes: " + nrOfShapes + " for ri: " + ri.getIngredient().getName());

            for (int i = 0; i < nrOfShapes; i++) {
                Shape s = Transformer.setShape(theSketch, ri);
                s.setColor(ri.getColor());
                s.step();
                shapeList.add(s);
//                System.out.println("shape: " + s.getClass() + " with color " + theSketch.hex(s.getColorValues()) +
//                                           " and position " + s.getX() + " " + s.getY() + " added");
            }
        }

        // assign the list of shapes to sketch
        theSketch.setShapes(shapeList);

        // after setting below boolean to true, the shapes will start to get drawn
        theSketch.setGenerate(true);

        // intermezzo. let the sketch run for time specified
        try {
            Thread.sleep(7777);
        } catch (InterruptedException e) {
            System.out.println("diagnostic: interrupted during sleep");
            e.printStackTrace();
        }

        theSketch.dispose();            // stops animation; does NOT close the window with the sketch
        this.pImg = theSketch.get();    // assign the image to variable pImg
        theSketch.exitActual();         // exits processing PApplet without closing the JVM! Important!

        // convert pImg to BufferedImage to String with help of Base64 encoder and PImage .getNative, which returns a
        // Buffered image from a PImage
        String imageString = imgToBase64String((BufferedImage) pImg.getNative(), "PNG");
        return imageString;
    }

    public Visual saveVisual(Recipe recipe, Long newId) {
        String fileName = "visual" + newId + ".png";

        // Determine the desired file path
        String filePath = "src/main/webapp/spawns";
        // Get the current working directory
        String currentDir = System.getProperty("user.dir");
        // Construct the full file path using the current working directory as a starting point
        Path imagePath = Paths.get(currentDir, filePath);
        // save the PImage (NOT the buffered, encoded one) to the specified folder
        this.pImg.save(imagePath + "/" + fileName);

        // create new Visual entity for storing location, chef etc.
        // TODO: createVisual in VisualService
        Visual newVisual = new Visual();
        String chefUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Chef chef;
        try {
            chef = chefService.findChefByUserName(chefUserName);
        } catch (UsernameNotFoundException unfe) {
            throw new SaveImageNotPossible("save image not possible when not logged in"); // todo: will never be
            // thrown as save option not available when not logged in
        }

        newVisual.setChef(chef);
        newVisual.setRecipe(recipe);
        newVisual.setFileName(fileName);
        newVisual.setFileLocation("/spawns/" + fileName);
        visualService.saveVisual(newVisual);

        Visual visual = visualService.findVisualById(newId);
        System.out.println(visual);     // diagnostic check

        return visual;
    }

    // help method to encode image to String with Base64 encoder
    private String imgToBase64String(final RenderedImage img, final String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, formatName, os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
        catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    // Processing PApplet initialization - bit weird, but hey, it works.
    private TheSketch getTheSketch() {
        System.setProperty("java.awt.headless", "false"); // app needs to be headfull to allow display functionality
        TheSketch theSketch = new TheSketch();
        String[] a = {"MAIN"};
        PApplet.runSketch(a, theSketch);
        return theSketch;
    }

}
