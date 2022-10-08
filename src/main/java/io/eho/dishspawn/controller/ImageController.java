package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.Parser;
import io.eho.dishspawn.graphics.processing.util.Transformer;
import io.eho.dishspawn.graphics.processing.shapes.Shape;
import io.eho.dishspawn.graphics.processing.TheSketch;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.eho.dishspawn.DishSpawnApplication.theSketch;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;
    private VisualService visualService;
    private ChefService chefService;

    @Autowired
    public ImageController(RecipeService recipeService, RecipeIngredientService recipeIngredientService,
                           VisualService visualService, ChefService chefService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.visualService = visualService;
        this.chefService = chefService;
    }

    @PostMapping("/spawn/{id}")
    public String generateSpawn(@PathVariable String id, Model model) {
        // get recipe
        // help method to convert String to Long and catch non-numerical input
        Long idLong = Parser.convertStringIdToLong(id);

        // todo - this does overlook the possibility 0 is provided per input
        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }
        Recipe recipe = recipeService.findRecipeById(idLong);

        // get recipe-ingredients
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);

        // TODO translate recipe-ingredients to visual properties
        // narrow down list to list with visual impact Y ri's
        List<RecipeIngredient> recipeIngredientsWithVisualImpact = recipeIngredientList.stream()
                .filter(ri -> ri.isVisualImpact() == true)
                .collect(Collectors.toList());

        // get a total of mass and amount
        int totalSize = recipeIngredientsWithVisualImpact.stream()
                .filter(ri -> ri.getMassOrVolume() > 0)
                .mapToInt(ri -> ri.getMassOrVolume())
                .sum();

        // SKETCH - todo refactor to static sketch
//        TheSketch theSketch = getTheSketch();
        theSketch.setGenerate(true);

        // transform each ri to a shape and place in list
        List<Shape> shapeList = new ArrayList<>();
        for (RecipeIngredient ri : recipeIngredientsWithVisualImpact) {
            Shape s = Transformer.setShape(theSketch, ri);
            s.setColor(ri.getColor());
            shapeList.add(s);
            System.out.println(s);
        }
        // set the list of shapes in the sketch
        theSketch.setShapes(shapeList);

        // short intermezzo. lets the sketch run for time specified
        try {
            Thread.sleep(7777);
        } catch (InterruptedException e) {
            System.out.println("diagnostic: interrupted during sleep");
            e.printStackTrace();
        }

        theSketch.dispose(); // stops animation; does NOT close the window with the sketch

        Long newId = visualService.findNextIdValue(); // get next_val hibernate sequence
        String fileName = "visual" + newId + ".png";
        theSketch.save("src/main/webapp/spawns/" + fileName); // save sketch as .png
        // todo: real save to move to saveImage method -> first 'save' to BufferedImage only (in memory)

        Visual newVisual = new Visual();
        newVisual.setRecipe(recipe);
        newVisual.setChef(chefService.findChefById(17l)); // todo: with security, get chef from user
        newVisual.setFileName(fileName);
        newVisual.setFileLocation("/spawns/" + fileName);

        visualService.saveVisual(newVisual);

        // re-initialize theSketch todo
        theSketch.setup();

        // read saved image from file location - not needed I think?
        Visual visual = visualService.findVisualById(newId);
        System.out.println(visual);

        // add recipe and visual to model / show on page
        model.addAttribute(recipe);
        model.addAttribute("visual", visual);
        return "redirect:/visual?visualId=" + newId;
    }

    private TheSketch getTheSketch() {
        System.setProperty("java.awt.headless", "false"); // app needs to be headfull to allow display functionality
        TheSketch theSketch = new TheSketch();
        String[] a = {"MAIN"};
        PApplet.runSketch(a, theSketch);
        return theSketch;
    }

}