package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;

import java.util.List;

public interface IngredientService {
    // create
    void saveIngredient(Ingredient ingredient);

    List<Ingredient> getAllIngredients();
}
