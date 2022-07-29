package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// by annotating with @RestController we'll enable this class to handle Http
// requests
@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

}
