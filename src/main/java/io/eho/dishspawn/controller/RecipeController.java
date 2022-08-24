package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
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

    @GetMapping("/")
    public String getLastRecipes() {

        return "redirect:/home/page/1";
//        return "index";
    }

    @GetMapping("/home/page/{pageNr}")
    public String getRecipePage(Model model, @PathVariable("pageNr") int currentPage) {

        // get recipes paged from DB
        Page<Recipe> recipePage = recipeService.findPage(currentPage);
        int totalPages = recipePage.getTotalPages();
        long totalRecipes = recipePage.getTotalElements();
        List<Recipe> recipes = recipePage.getContent();

        // get out most recent recipe
        Recipe lastRecipe = recipes.get(0);

        // TODO: remove most recent entry from list
//        recipes.remove(0);

        // add most recent and list to model
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalRecipes", totalRecipes);
        model.addAttribute("lastRecipe", lastRecipe);
        model.addAttribute("recipes", recipes);

        // use model in index view
        return "index";
    }

    // get methods
    // all recipes
    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipesFromDB = recipeService.getAllRecipes();

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
