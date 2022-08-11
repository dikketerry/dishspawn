package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllIngredients();
}
