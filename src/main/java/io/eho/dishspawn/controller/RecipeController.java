package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.RecipeService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final VisualService visualService;

    @Autowired
    public RecipeController(RecipeService recipeService, VisualService visualService) {
        this.recipeService = recipeService;
        this.visualService = visualService;
    }

    // recipe page is shown with latest visual. TODO: same layout as home
    @GetMapping("/recipe")
    public String showRecipe(@RequestParam Long recipeId, Model model) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        Visual recipeLastVisual = visualService.findLastVisualForRecipe(recipe);

        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeLastVisual", recipeLastVisual);

        return "recipe";
    }

    // show all recipes
    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipesFromDB = recipeService.findAllRecipes();

        // TODO link to 4xx
//        if (recipesFromDB == null) {
//            model.addAttribute("error", "no recipes found");
//            return "error-page";
//        }

        model.addAttribute("recipes", recipesFromDB);
        return "all-recipes"; // TODO - layout page
    }

    // form to add new recipe TODO
    @GetMapping("/recipe/add")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "add-recipe";
    }

    @PostMapping("/recipe/save")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:all";
    }

}
