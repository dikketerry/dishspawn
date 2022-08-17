package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    // property RecipeService
    private RecipeService recipeService;

    // dependency injection via constructor
    public RecipeController() {

    }

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // get methods
    // all recipes
    @GetMapping("/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipesFromDB = recipeService.getAllRecipes();

        if (recipesFromDB == null) {
            model.addAttribute("error", "no recipes found");
            return "error-page";
        }

        model.addAttribute("recipes", recipesFromDB);
        return "all-recipes";
    }

    @GetMapping("/add")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "add-recipe";
    }

    // recipe(s) with ingredients

    // post - save
    @PostMapping("save")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:all";
    }

}
