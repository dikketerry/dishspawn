package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("")
public class SpawnController {

    // set required properties to spawn:
    // 1. ingredient service to find ingredient id's DB based on input user
    // 2. recipeingredient service to find recipeingredients with ingredient
    // ids and provide belonging recipe ids
    // 3. recipeservice to collect belonging recipes

    private IngredientService ingredientService;
    private RecipeIngredientService recipeIngredientService;
    private RecipeService recipeService;

    private Set<Ingredient> ingredientSearchResult = new HashSet<>();
    private Set<Ingredient> ingredientSpawnSet = new HashSet<>();

    // dependency injection via constructor
    public SpawnController() { }

    @Autowired
    public SpawnController(IngredientService ingredientService,
                           RecipeIngredientService recipeIngredientService,
                           RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/spawn")
    public String spawnGet(Model model) {
        model.addAttribute("ingredientSearchResult", ingredientSearchResult);
        model.addAttribute("ingredientSpawnSet", ingredientSpawnSet);
        return "spawn-dummy";
    }

    @PostMapping("/spawn")
    public String spawnPost(String name) {
        Set<Ingredient> ingredientsTemp =
                ingredientService.getAllIngredientsByNameContaining(name);

        for (Ingredient ingredient : ingredientsTemp) {
            ingredientSearchResult.add(ingredient);
            System.out.println("found: " + ingredient); // diagnostic print
        }
        return "redirect:/spawn";
    }

    @PostMapping("/spawn/add-ingredient")
    public String spawnPostAddIngredient(Ingredient ingredient) {
        // user confirms adding ingredient x to spawn selection - which is
        // the ingredient that comes in as input - means the ingredient object
        // needs to be bound to the post action (a button)
        System.out.println("Ingredient coming in as parameter: " + ingredient);
        ingredientSpawnSet.add(ingredient);
        ingredientSearchResult.remove(ingredient);

        // diagnostic prints
        for (Ingredient tempIngredient : ingredientSpawnSet) {
            System.out.println("Ingredient SpawnSet: " + tempIngredient.getName());
        }

        for (Ingredient tempIngredient : ingredientSearchResult) {
            System.out.println("Ingredient Search Result: " + tempIngredient.getName());
        }

        return "redirect:/spawn";
    }


}
