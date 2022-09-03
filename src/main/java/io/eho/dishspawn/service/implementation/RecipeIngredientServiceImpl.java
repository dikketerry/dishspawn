package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.repository.RecipeIngredientRepository;
import io.eho.dishspawn.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override // TODO add Recipe as parameter
    public void saveRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.save(recipeIngredient);
    }

    @Override
    public List<RecipeIngredient> findAllRecipeIngredients() {
        return null;
    }

    @Override
    public List<RecipeIngredient> findAllRecipeIngredientsForRecipe(Recipe recipe) {
        return null;
    }

    @Override
//    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Recipe> findAllRecipeIngredientsByIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public Page findPageRecipeIngredientsByIngredient(Ingredient ingredient, int pageNr) {

        Pageable pageable = PageRequest.of(pageNr - 1, 3, Sort.Direction.ASC, "timestampCreated");
        return recipeIngredientRepository.findAllByIngredient(ingredient, pageable);

    }

}
