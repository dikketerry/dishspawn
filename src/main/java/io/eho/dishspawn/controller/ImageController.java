package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.utils.Parser;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ImageService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/spawn")
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    // key under which a freshly generated, not-yet-saved spawn PNG lives in the user's session
    static final String PENDING_IMAGE = "pendingSpawnImage";

    @Autowired
    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    // generate image
    @PostMapping("/spawn/{id}")
    public String generateImage(@PathVariable String id, Model model, HttpSession session) {
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

        // delegate to imageService; keep the raw PNG in THIS user's session until they save
        ImageService.GeneratedImage generated = imageService.generateImage(recipe);
        session.setAttribute(PENDING_IMAGE, generated.pngBytes());
        model.addAttribute("recipe", recipe);
        model.addAttribute("imageString", generated.previewBase64());

        return "tempvisual";
    }

    // save image and Visual entity
    // TODO: move logic to ImageService
    @PostMapping("/spawn/{id}/save")
    public String saveVisual(@PathVariable String id, Model model, HttpSession session) {
        Long idLong = Parser.convertStringIdToLong(id);
        if (idLong == 0l) {
            model.addAttribute("error", id + " is not a numeric format");
            return "error-page";
        }

        byte[] pngBytes = (byte[]) session.getAttribute(PENDING_IMAGE);
        if (pngBytes == null) {
            // nothing generated in this session (stale tab, double submit, direct hit) — back to spawn
            return "redirect:/spawn";
        }

        Recipe recipe = recipeService.findRecipeById(idLong);

        // delegate with this session's image, then clear it
        Visual visual = imageService.saveVisual(recipe, pngBytes);
        session.removeAttribute(PENDING_IMAGE);

        return "redirect:/visual?visualId=" + visual.getId();
    }
}