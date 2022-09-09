package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.Parser;
import io.eho.dishspawn.graphics.ImageCanvas;
import io.eho.dishspawn.graphics.ImageWindow;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.play.sketchtest.vanillajava.Image;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;

    private static int spawnCounter;

    @Autowired
    public ImageController(RecipeService recipeService, RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping("/spawn/{id}")
    public String spawnGo(@PathVariable String id, Model model) {
        // increase counter (needed for filename)
        spawnCounter++;

        // get recipe
        // help method to convert String to Long and catch non-numerical input
        Long idLong = Parser.convertStringIdToLong(id);


        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }
        Recipe recipe = recipeService.findRecipeById(idLong);

        // get recipe-ingredients
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientsForRecipe(recipe);

        // translate recipe-ingredients to visual properties

        // generate sketch 1
//        int a = 127;
//        int r = 244;
//        int g = 241;
//        int b = 134;
//        int color = (a << 24) | (r << 16) | (g << 8) | b;
//        BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
//
//        for (int x = 0; x < 400; x++) {
//            // diagnostic print
//            if (x == 100) {
//                System.out.println("in loop to assign color to pixels");
//            }
//            for (int y = 0; y < 400; y++) {
//                img.setRGB(x, y, color);
//            }
//        }
//
//        String pathName = "src/main/resources/static/img/image" + spawnCounter + ".png";
//        File imageFile = new File(pathName);
//
//        try {
//            // diagnostic print
//            System.out.println("in try write file method");
//            ImageIO.write(img, "PNG", imageFile);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }

        // generate sketch 2
        System.setProperty("java.awt.headless", "false");
        JFrame window = new ImageWindow();
        ImageCanvas canvas = new ImageCanvas();
        window.add(canvas);
        canvas.draw();
        window.setVisible(true);

//		SwingUtilities.invokeLater(() -> {
//			JFrame window = new ImageWindow();
//			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			window.setVisible(true);
//		});



//        Image img = new Image();
//        img.setColorBufferedImage();

        // show generated image in browser

        // save image (should move to saveImage method)
        // relative path: "/src/main/resources/static/img/image" + counter + ".png"

//        PlaySketch playSketch = new PlaySketch();
//        PApplet.main("PlaySketch");

        model.addAttribute(recipe);
        return "spawn-o";
    }

}
