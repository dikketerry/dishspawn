package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameContaining(String name);
    Page<Ingredient> findAllByNameContaining(String name, Pageable pageable);
//    List<Ingredient> getAllByIngredientCategory(IngredientCategory ingredientCategory);

}
