package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

//    RecipeIngredient findById(RecipeIngredient recipeIngredient);
//    RecipeIngredient getById(RecipeIngredient recipeIngredient);
//    List<RecipeIngredient> findByRecipe(Recipe recipe);
//    Page<RecipeIngredient> findAllByNameContaining(String name, Pageable pageable);
    Page findAllByIngredient(Ingredient ingredient, Pageable pageable);
}
