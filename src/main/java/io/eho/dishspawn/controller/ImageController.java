package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.Parser;
import io.eho.dishspawn.exception.SaveImageNotPossible;
import io.eho.dishspawn.graphics.processing.util.Transformer;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.graphics.processing.TheSketch;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;
    private final VisualService visualService;
    private final ChefService chefService;

    private TheSketch theSketch;    // custom PApplet class (Processing)
    private PImage pImg;            // Processing class, used for
    private Recipe recipe;

    @Autowired
    public ImageController(RecipeService recipeService, RecipeIngredientService recipeIngredientService,
                           VisualService visualService, ChefService chefService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.visualService = visualService;
        this.chefService = chefService;
    }

    // generate image
    @PostMapping("/spawn/{id}")
    public String generateSpawn(@PathVariable String id, Model model) {
        // help method to convert String to Long and catch non-numerical input
        Long idLong = Parser.convertStringIdToLong(id);
        this.pImg = null;

        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }
        // get recipe
        Recipe recipe = recipeService.findRecipeById(idLong);
        this.recipe = recipe;

        // get recipe-ingredients
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);

        // narrow down list to list with visual impact Y ri's
        List<RecipeIngredient> recipeIngredientsWithVisualImpact = recipeIngredientList.stream()
                .filter(ri -> ri.isVisualImpact() == true)
                .collect(Collectors.toList());

        // get a total of mass and amount - TODO: NOT USED YET - FALLS UNDER ALGO WORK
        int totalSize = recipeIngredientsWithVisualImpact.stream()
                .filter(ri -> ri.getMassOrVolume() > 0)
                .mapToInt(ri -> ri.getMassOrVolume())
                .sum();

        // initiate the sketch (the Processing PApplet)
        theSketch = getTheSketch();

        // transform each ri to a shape, place in a list and assign to the active sketch
        List<Shape> shapeList = new ArrayList<>();
        for (RecipeIngredient ri : recipeIngredientsWithVisualImpact) {
            Shape s = Transformer.setShape(theSketch, ri); // note: this does NOT assign the shape yet to the sketch!
            s.setColor(ri.getColor());
            shapeList.add(s);
            System.out.println(s);      // diagnostic - print the shapes in list
        }

        // assign the list of shapes to sketch
        theSketch.setShapes(shapeList);

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

        model.addAttribute("recipe", recipe);
        model.addAttribute("imageString", imageString);

        return "tempvisual";
    }

    // save image and Visual entity
    @PostMapping("/spawn/{id}/save")
    public String saveVisual(Model model) {
        Long newId = visualService.findNextIdValue(); // get next_val hibernate sequence
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
        newVisual.setRecipe(this.recipe);
        newVisual.setFileName(fileName);
        newVisual.setFileLocation("/spawns/" + fileName);
        visualService.saveVisual(newVisual);

        Visual visual = visualService.findVisualById(newId);
        System.out.println(visual);     // diagnostic check

        model.addAttribute(recipe);
        model.addAttribute("visual", visual);
        return "redirect:/visual?visualId=" + newId;
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