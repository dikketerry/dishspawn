package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientService {

    void saveRecipeIngredient(RecipeIngredient recipeIngredient);

    List<RecipeIngredient> getAllRecipeIngredients();

    List<RecipeIngredient> getAllRecipeIngredientsForRecipe(Recipe recipe);

    List<Recipe> findAllRecipesByIngredient(Ingredient ingredient);


}
