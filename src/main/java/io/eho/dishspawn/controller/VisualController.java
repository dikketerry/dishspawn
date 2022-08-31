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

//    private Visual latestVisual;

    @Autowired
    public VisualController(VisualService visualService, RecipeService recipeService) {
        this.visualService = visualService;
        this.recipeService = recipeService;
    }

    @GetMapping("/home/page/{pageNr}")
    @Primary // TODO
    public String getVisualsPaged(@PathVariable(name = "pageNr", required = false)int currentPage,
                                  Model model) {

        // get latest visual
        Visual latestVisual = visualService.findLatestVisual();
//        List<Visual> allVisuals = visualService.findAllVisuals();

        // get all visuals paginated - ensure the first visual is not shown
        // in the view
        Page<Visual> visualPage = visualService.findPageVisuals(currentPage);

        List<Visual> pagedVisuals;

        int totalPages = visualPage.getTotalPages();
        long totalVisuals = visualPage.getTotalElements();
        pagedVisuals = visualPage.getContent();

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
//        Recipe recipeOfVisual = recipeService.findRecipeById(visual.getRecipe().getId());


//        diagnostic print
        System.out.println(visual);

//        Long recipeId = visual.getRecipe().getId();
//        System.out.println("recipe id: " + recipeId);

//        Recipe r = visual.getRecipe();
//        System.out.println("Recipe: " + r); // NULLLL???? WHAT THE F***...

//        Chef c = visual.getChef();
//        System.out.println("Chef: " + c);

//
//


        model.addAttribute("visual", visual);
//        model.addAttribute("recipe", recipeOfVisual);

        return "visual";
    }

}
