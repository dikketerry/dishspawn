package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.Parser;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.graphics.processing.TheSketch;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import io.eho.dishspawn.service.implementation.ChefServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import processing.core.PApplet;

import java.util.List;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;
    private VisualService visualService;
    private ChefService chefService;

    @Autowired
    public ImageController(RecipeService recipeService, RecipeIngredientService recipeIngredientService,
                           VisualService visualService, ChefServiceImpl chefService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.visualService = visualService;
        this.chefService = chefService;
    }

    @PostMapping("/spawn/{id}")
    public String spawnGo(@PathVariable String id, Model model) {
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
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findAllRecipeIngredientByRecipe(recipe);

        // TODO translate recipe-ingredients to visual properties

        // SKETCH
        System.setProperty("java.awt.headless", "false"); // app needs to be headfull to allow functions which make
        // use of display / mouse / keyboard

        // alternative to get an instance of the PApplet
        TheSketch theSketch = new TheSketch();
        String[] a = {"MAIN"};
        PApplet.runSketch(a, theSketch);

        // short intermezzo. lets the sketch run for time specified
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("diagnostic: interrupted during sleep");
            e.printStackTrace();
        }

        theSketch.dispose(); // stops animation; does NOT close the window with the sketch
        // todo: idea: make the sketch static, re-use it for each new spawn (clean it up, intialize, fill new)

        Long newId = visualService.findNextIdValue(); // get next_val hibernate sequence
        String fileName = "visual" + newId + ".png";
        theSketch.save("src/main/resources/static/img/spawns/" + fileName); // save sketch as .png
        // todo: real save to move to saveImage method -> first 'save' to BufferedImage only (in memory)

        // assign .png to new Visual
        Visual newVisual = new Visual();
        newVisual.setRecipe(recipe);
        newVisual.setChef(chefService.findChefById(17l)); // todo: with security, get chef from user
        newVisual.setFileName(fileName);
        newVisual.setFileLocation("/img/spawns/" + fileName);
        visualService.saveVisual(newVisual);

        // short intermezzo. testing if time is needed to get img shown in browser (not working yet)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("diagnostic: interrupted during sleep");
            e.printStackTrace();
        }

        // read saved image from file location - not needed I think?
        Visual visual = visualService.findVisualById(newId);
        System.out.println(visual);

        // add recipe and visual to model / show on page
        model.addAttribute(recipe);
        model.addAttribute("visual", visual);
        return "redirect:/visual?visualId=" + newId;
//        return "spawn-o";
    }

}

/* code i don't (yet) want to throw away

        // generate sketch 1
        int a = 127;
        int r = 244;
        int g = 241;
        int b = 134;
        int color = (a << 24) | (r << 16) | (g << 8) | b;
        BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < 400; x++) {
            // diagnostic print
            if (x == 100) {
                System.out.println("in loop to assign color to pixels");
            }
            for (int y = 0; y < 400; y++) {
                img.setRGB(x, y, color);
            }
        }

        String pathName = "src/main/resources/static/img/image" + spawnCounter + ".png";
        File imageFile = new File(pathName);

        try {
            // diagnostic print
            System.out.println("in try write file method");
            ImageIO.write(img, "PNG", imageFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

//         generate sketch 2



        SketchThread sketchThread = new SketchThread();
        sketchThread.start();

        try {
            sketchThread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sketchThread.doStop();

        Runnable runnable = new TheSketch();
        Thread thread = new Thread(runnable);
        thread.start();
        PApplet.main("io.eho.dishspawn.graphics.processing.TheSketch");





        JFrame window = new ImageWindow();
        window.setSize(ImageWindow.WIDTH, ImageWindow.HEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        ImageCanvas canvas = new ImageCanvas();
        window.add(canvas);
        canvas.draw();
        window.setVisible(true);

//         below not needed right now
		SwingUtilities.invokeLater(() -> {
			JFrame window = new ImageWindow();
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setVisible(true);
		});

        Image img = new Image();
        img.setColorBufferedImage();
 */
