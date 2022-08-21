package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.repository.RecipeIngredientRepository;
import io.eho.dishspawn.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    // add RecipeIngredientRepo property
    private RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientServiceImpl() {

    }

    // dependency injection via constructor
    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public void saveRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public List<RecipeIngredient> getAllRecipeIngredients() {
        return null;
    }

    @Override
    public List<RecipeIngredient> getAllRecipeIngredientsForRecipe(Recipe recipe) {
        return null;
    }

    @Override
//    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Recipe> findAllRecipesByIngredient(Ingredient ingredient) {
        return null;
    }
}
