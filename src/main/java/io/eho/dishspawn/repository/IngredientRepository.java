package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // all crud methods

//    Ingredient findByName(String name);
//    Ingredient findById(Long id);
//    List<Ingredient> getAllByIngredientCategory(IngredientCategory ingredientCategory);

}
