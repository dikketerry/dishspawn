package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// because JpaRepository is extended, there is no need to annotate this repo
// with @Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    // all crud database methods

    List<Recipe> findAllRecipesWithIngredientName(String ingredientName);

    // findAllRecipesByIngredientName (String ingredientName)
    // 1. collect the ingredient from Ingredient table
    // 2. check ingredient in RecipeIngredient table
    // 3. if match, return recipe from RecipeIngredient table


}

