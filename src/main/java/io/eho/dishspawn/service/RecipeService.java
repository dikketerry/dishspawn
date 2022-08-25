package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Recipe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {

    Recipe findById(Long id);

    void saveRecipe(Recipe recipe);

    List<Recipe> getAllRecipes();

    Page<Recipe> findPage(int pageNr);
}
