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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;
    private VisualService visualService;
    private ChefService chefService;

    private TheSketch theSketch;
    private PImage pImg;
    private Recipe recipe;

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
        this.pImg = null;

        // todo - this does overlook the possibility 0 is provided per input
        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }
        Recipe recipe = recipeService.findRecipeById(idLong);
        this.recipe = recipe;

        // get recipe-ingredients
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);

        // todo translate recipe-ingredients to visual properties
        // narrow down list to list with visual impact Y ri's
        List<RecipeIngredient> recipeIngredientsWithVisualImpact = recipeIngredientList.stream()
                .filter(ri -> ri.isVisualImpact() == true)
                .collect(Collectors.toList());

        // get a total of mass and amount
        int totalSize = recipeIngredientsWithVisualImpact.stream()
                .filter(ri -> ri.getMassOrVolume() > 0)
                .mapToInt(ri -> ri.getMassOrVolume())
                .sum();

        // initiate the sketch
        theSketch = getTheSketch();

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
        this.pImg = theSketch.get();
        theSketch.exitActual();
        String imageString = imgToBase64String((BufferedImage) pImg.getNative(), "PNG");

        model.addAttribute("recipe", recipe);
        model.addAttribute("imageString", imageString);

        return "tempvisual";
    }

    @PostMapping("/spawn/{id}/save")
    public String saveVisual(Model model) {
        Long newId = visualService.findNextIdValue(); // get next_val hibernate sequence
        String fileName = "visual" + newId + ".png";

        this.pImg.save("src/main/webapp/spawns/" + fileName);

        Visual newVisual = new Visual();
        String chefUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Chef chef = null;
        try {
            chef = chefService.findChefByUserName(chefUserName);
        } catch (UsernameNotFoundException unfe) {
            throw new SaveImageNotPossible("save image not possible when not logged in");
        }

        newVisual.setChef(chef);
        newVisual.setRecipe(this.recipe);
        newVisual.setFileName(fileName);
        newVisual.setFileLocation("/spawns/" + fileName);
        visualService.saveVisual(newVisual);

        // re-initialize theSketch todo
        // reset the spawn selector

        Visual visual = visualService.findVisualById(newId);
        System.out.println(visual);

        // add recipe and visual to model / show on page
        model.addAttribute(recipe);
        model.addAttribute("visual", visual);
        return "redirect:/visual?visualId=" + newId;
    }

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

    private TheSketch getTheSketch() {
		System.setProperty("java.awt.headless", "false"); // app needs to be headfull to allow display functionality
		TheSketch theSketch = new TheSketch();
		String[] a = {"MAIN"};
		PApplet.runSketch(a, theSketch);
		return theSketch;
	}
}