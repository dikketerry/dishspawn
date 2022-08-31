package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    // property RecipeService
    private RecipeService recipeService;

    // property lastRecipe - global, needed in multiple methods
    private Recipe lastRecipe;

    // dependency injection via constructor
    public RecipeController() {

    }

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // add get mapping for "/home/page/pageNr" (paginated version of recipes)
    @GetMapping("/home-old/page/{pageNr}")
    public String showRecipe(Model model, @PathVariable("pageNr") int currentPage) {

        // get recipes paged from DB
        Page<Recipe> recipePage = recipeService.findPage(currentPage);
        int totalPages = recipePage.getTotalPages();
        long totalRecipes = recipePage.getTotalElements();
        List<Recipe> recipes = recipePage.getContent();

        // get out most recent recipe
        lastRecipe = recipes.get(0);

        // TODO: remove most recent entry from list - might need a
        //  work-around @service level; generate full list first, split it in
        //  2 (last Recipe vs. the rest of recipes)
//        recipes.remove(0);

        // add most recent and list to model
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalRecipes", totalRecipes);
        model.addAttribute("lastRecipe", lastRecipe);
        model.addAttribute("recipes", recipes);

        // use model in index view
        return "home-old";
    }

    // add get mapping for "/recipe/{id}" - should contain paginated version
    // of belonging visuals
    @GetMapping("/recipe")
    public String showRecipe(@RequestParam Long recipeId,
                             Model model) {
        Recipe recipe = recipeService.findRecipeById(recipeId);

        model.addAttribute(recipe);

        return "recipe";
    }

    // add get mapping for "/recipe/all"
    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipesFromDB = recipeService.findAllRecipes();

        if (recipesFromDB == null) {
            model.addAttribute("error", "no recipes found");
            return "error-page";
        }

        model.addAttribute("recipes", recipesFromDB);
        return "all-recipes";
    }

    @GetMapping("/recipe/add")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "add-recipe";
    }

    // recipe(s) with ingredients

    // post - save
    @PostMapping("/recipe/save")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:all";
    }

}
