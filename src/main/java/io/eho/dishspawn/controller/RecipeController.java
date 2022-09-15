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

    private RecipeService recipeService;
    private VisualService visualService;

    // property lastRecipe - global, needed in multiple methods
    private Recipe lastRecipe;

    public RecipeController() {}

    @Autowired
    public RecipeController(RecipeService recipeService, VisualService visualService) {
        this.recipeService = recipeService;
        this.visualService = visualService;
    }

    // recipe page is shown with latest visual. TODO: functionality to browse different visuals for recipe
    @GetMapping("/recipe")
    public String showRecipe(@RequestParam Long recipeId,
                             Model model) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        Visual recipeLastVisual = visualService.findLastVisualForRecipe(recipe);
        System.out.println(recipe);

        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeLastVisual", recipeLastVisual);

        return "recipe";
    }

    // add get mapping for "/recipe/all"
    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipesFromDB = recipeService.findAllRecipes();

        if (recipesFromDB == null) {
            model.addAttribute("error", "no recipes found");
            return "error-page"; // TODO - layout page
        }

        model.addAttribute("recipes", recipesFromDB);
        return "all-recipes"; // TODO - layout page
    }

    @GetMapping("/recipe/add")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "add-recipe"; // TODO: complete form
    }

    // recipe(s) with ingredients

    // post - save
    @PostMapping("/recipe/save")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:all";
    }

}
