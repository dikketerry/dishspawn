package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.repository.RecipeRepository;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    public RecipeServiceImpl() {

    }

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.getReferenceById(id);
    }

    @Override // TODO: add Chef as parameter
    public void saveRecipe(Recipe recipe) {
        this.recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAllByOrderByTimestampCreatedDesc();
    }

    @Override
    public Page<Recipe> findPage(int pageNr) {
        Pageable pageable = PageRequest.of(pageNr - 1, 4,
                                           Sort.by(Sort.Direction.DESC,
                                                   "timestampCreated"));

        return recipeRepository.findAll(pageable);
    }
}
