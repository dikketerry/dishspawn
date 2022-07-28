package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.util.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Integer> {

    Ingredient findByName(String name);
    Ingredient findById(Long id);
    List<Ingredient> getAllByIngredientCategory(IngredientCategory ingredientCategory);

}
