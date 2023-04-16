package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.graphics.processing.util.Randomizer;
import io.eho.dishspawn.graphics.processing.util.Transformer;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.ImageService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String generateImage(Recipe recipe) {
//        // get recipe-ingredients
//        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);
//        // filter recipe-ingredients with visual impact and mass/volume > 0, sort on mass (biggest first)
//        List<RecipeIngredient> recipeIngredientsWithVisualImpactSortedOnMass = recipeIngredientList.stream()
//                .filter(ri -> ri.isVisualImpact() == true && ri.getMassOrVolume() > 0)
//                .sorted(Comparator.comparingDouble(RecipeIngredient::getMassOrVolume).reversed())
//                .collect(Collectors.toList());
//
//        // filter recipe-ingredients with visual impact and mass/volume == 0
//        List<RecipeIngredient> recipeIngredientsWithVisualImpactNoMass = recipeIngredientList.stream()
//                .filter(ri -> ri.isVisualImpact() == true && ri.getMassOrVolume() == 0)
//                .collect(Collectors.toList());
//
//        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
//            System.out.println("ri mass / volume > 0: " + ri.getMassOrVolume());
//        }
//
//        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactNoMass) {
//            System.out.println("ri mass / volume == 0: " + ri.getMassOrVolume());
//        }
//
//        // get a total of mass / volume
//        int totalSize = 0;
//        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
//            totalSize += ri.getMassOrVolume();
//        }
//        System.out.println("total mass: " + totalSize);
//
//        // initiate the sketch (the Processing PApplet)
//        theSketch = getTheSketch();
//
//        // init a list for storing shapes
//        List<Shape> shapeList = new ArrayList<>();
//
//        // set total # of shapes
//        int totalShapes = Randomizer.getRandomNumberInRange(120, 180); // this can become another dependency on
//        // recipe characteristics
//        System.out.println("total shapes: " + totalShapes);
//
//        // for riMass-list, loop
//        for (RecipeIngredient ri : recipeIngredientsWithVisualImpactSortedOnMass) {
//            double riSize = ri.getMassOrVolume();
//            System.out.println("name: " + ri.getIngredient().getName() + "; Color hex: " + ri.getColor() + "; Mass: " + riSize);
//
//            // set shape per ri
//
//            int nrOfShapes = (int) Math.ceil((riSize / totalSize) * totalShapes);
//            System.out.println("nr of shapes: " + nrOfShapes + " for ri: " + ri.getIngredient().getName());
//
//            for (int i = 0; i < nrOfShapes; i++) {
//                Shape s = Transformer.setShape(theSketch, ri);
//                s.setColor(ri.getColor());
//                s.step();
//                shapeList.add(s);
////                System.out.println("shape: " + s.getClass() + " with color " + theSketch.hex(s.getColorValues()) +
////                                           " and position " + s.getX() + " " + s.getY() + " added");
//            }
//        }
//
//        // assign the list of shapes to sketch
//        theSketch.setShapes(shapeList);
//
//        // after setting below boolean to true, the shapes will start to get drawn
//        theSketch.setGenerate(true);
//
//        // intermezzo. let the sketch run for time specified
//        try {
//            Thread.sleep(7777);
//        } catch (InterruptedException e) {
//            System.out.println("diagnostic: interrupted during sleep");
//            e.printStackTrace();
//        }
//
//        theSketch.dispose();            // stops animation; does NOT close the window with the sketch
//        this.pImg = theSketch.get();    // assign the image to variable pImg
//        theSketch.exitActual();         // exits processing PApplet without closing the JVM! Important!
//
//        // convert pImg to BufferedImage to String with help of Base64 encoder and PImage .getNative, which returns a
//        // Buffered image from a PImage
//        String imageString = imgToBase64String((BufferedImage) pImg.getNative(), "PNG");
//        return imageString;
        return "test";
    }
}
