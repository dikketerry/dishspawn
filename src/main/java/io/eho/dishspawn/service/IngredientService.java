package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {
    // create
    void saveIngredient(Ingredient ingredient);

    List<Ingredient> getAllIngredients();

    Set<Ingredient> getAllIngredientsByNameContaining(String name);

    Ingredient findIngredientById(Long id);
}
