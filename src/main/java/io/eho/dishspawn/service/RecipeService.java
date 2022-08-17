package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Recipe;

import java.util.List;

public interface RecipeService {

    void saveRecipe(Recipe recipe);

    List<Recipe> getAllRecipes();
}
