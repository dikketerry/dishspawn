package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.utils.Parser;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ImageService;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;
    private final VisualService visualService;

    private Recipe recipe;

    @Autowired
    public ImageController(RecipeService recipeService, ImageService imageService, VisualService visualService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.visualService = visualService;
    }

    // generate image
    @PostMapping("/spawn/{id}")
    public String generateImage(@PathVariable String id, Model model) {
        // ensure pImg is empty

        // help method to convert String to Long and catch non-numerical input
        // TODO: should be Util
        Long idLong = Parser.convertStringIdToLong(id);

        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page"; // todo: error page not existing anymore
        }
        // get recipe
        Recipe recipe = recipeService.findRecipeById(idLong);
        this.recipe = recipe;

        // delegate to imageService
        String imageString = imageService.generateImage(recipe);

        model.addAttribute("recipe", recipe);
        model.addAttribute("imageString", imageString);

        return "tempvisual";
    }

    // save image and Visual entity
    // TODO: move logic to ImageService
    @PostMapping("/spawn/{id}/save")
    public String saveVisual(Model model) {
        Recipe recipe = this.recipe;
        Long newId = visualService.findNextIdValue(); // get next_val hibernate sequence

        // delegate to imageService
        Visual visual = imageService.saveVisual(recipe, newId);

        model.addAttribute(recipe);
        model.addAttribute("visual", visual);
        return "redirect:/visual?visualId=" + newId;
    }
}