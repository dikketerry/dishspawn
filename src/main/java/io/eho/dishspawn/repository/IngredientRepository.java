package io.eho.dishspawn.repository;

import io.eho.dishspawn.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // all crud methods

    Set<Ingredient> findAllByNameContaining(String name);
    Page<Ingredient> findAllByNameContaining(String name, Pageable pageable);
//    Ingredient findById(Long id);
//    List<Ingredient> getAllByIngredientCategory(IngredientCategory ingredientCategory);

}
