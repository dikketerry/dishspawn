package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Ingredient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IngredientService {

    void saveIngredient(Ingredient ingredient);
    List<Ingredient> findAllIngredients();
    List<Ingredient> findAllIngredientsByNameContaining(String searchKey);
    Page<Ingredient> findPageIngredientsByNameContaining(String searchKey, int pageNr);
    Ingredient findIngredientById(Long id);

}
