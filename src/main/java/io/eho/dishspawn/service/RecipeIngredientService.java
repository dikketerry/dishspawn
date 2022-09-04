package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeIngredientService {

    void saveRecipeIngredient(RecipeIngredient recipeIngredient);
    List<RecipeIngredient> findAllRecipeIngredients();
    List<RecipeIngredient> findAllRecipeIngredientsForRecipe(Recipe recipe);
    List<RecipeIngredient> findAllRecipeIngredientByIngredient(Ingredient ingredient);

    Page findPageRecipeIngredientsByIngredient(Ingredient ingredient, int pageNr);
}
