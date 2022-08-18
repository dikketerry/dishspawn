package io.eho.dishspawn.controller;

import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Access;

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
    public String spawn() {
        return "spawn-dummy";
    }
}
