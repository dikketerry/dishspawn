package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    void saveIngredient(Ingredient ingredient);
    List<Ingredient> findAllIngredients();
    Set<Ingredient> findAllIngredientsByNameContaining(String searchKey);
    Page<Ingredient> findPageIngredientsByNameContaining(String searchKey, int pageNr);
    Page<Ingredient> findPageIngredients(int pageNr);
    Ingredient findIngredientById(Long id);

}
