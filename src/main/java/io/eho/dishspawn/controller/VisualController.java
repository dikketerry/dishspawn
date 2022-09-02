package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VisualController {

    private VisualService visualService;
    private RecipeService recipeService;

    @Autowired
    public VisualController(VisualService visualService, RecipeService recipeService) {
        this.visualService = visualService;
        this.recipeService = recipeService;
    }

    @GetMapping("/home/page/{pageNr}")
    public String getVisualsPaged(@PathVariable(name = "pageNr", required = false)int currentPage,
                                  Model model) {

        // get latest visual
        Visual latestVisual = visualService.findLatestVisual();

        // get paged visuals
        Page<Visual> visualPage = visualService.findPageVisuals(currentPage);

        List<Visual> pagedVisuals = visualPage.getContent();

        int totalPages = visualPage.getTotalPages();
        long totalVisuals = visualPage.getTotalElements();

        model.addAttribute("latestVisual", latestVisual);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalVisuals", totalVisuals);
        model.addAttribute("pagedVisuals", pagedVisuals);

        // diagnostic print
        for (Visual visual : pagedVisuals) {
            System.out.println(visual);
        }

        return "home";
    }

    @GetMapping("/visual")
    public String showRecipe(@RequestParam Long visualId,
                             Model model) {

        Visual visual = visualService.findVisualById(visualId);

//        diagnostic print
        System.out.println(visual);

        model.addAttribute("visual", visual);

        return "visual";
    }

}
