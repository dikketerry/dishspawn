package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.repository.IngredientRepository;
import io.eho.dishspawn.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// by annotating with @RestController we'll enable this class to handle Http
// requests
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("")
    public List<Ingredient> findAllIngredients() {
        return ingredientService.findAllIngredients();
    }

}
