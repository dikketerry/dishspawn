package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepo extends JpaRepository<RecipeIngredient, Long> {
    RecipeIngredient findById(RecipeIngredient recipeIngredient);
    RecipeIngredient getById(RecipeIngredient recipeIngredient);
    List<RecipeIngredient> findByRecipe(Recipe recipe);

}
