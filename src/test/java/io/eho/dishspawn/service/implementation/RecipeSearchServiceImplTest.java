package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeSearchServiceImplTest {

    @Mock
    private RecipeIngredientService recipeIngredientService;

    @InjectMocks
    private RecipeSearchServiceImpl service;

    @Test
    void zeroIngredients_isInvalid() {
        RecipeSearchService.Result result = service.findRecipesContainingAll(List.of(), 1);
        assertFalse(result.ingredientCountValid());
        assertTrue(result.recipes().isEmpty());
    }

    @Test
    void fourIngredients_isInvalid() {
        RecipeSearchService.Result result = service.findRecipesContainingAll(
                List.of(new Ingredient(), new Ingredient(), new Ingredient(), new Ingredient()), 1);
        assertFalse(result.ingredientCountValid());
    }

    @Test
    void oneIngredient_returnsThatIngredientsRecipes() {
        Ingredient egg = new Ingredient();
        Recipe omelette = new Recipe();
        when(recipeIngredientService.findPageRecipeIngredientsByIngredient(egg, 1))
                .thenReturn(new PageImpl<>(List.of(ri(omelette)), PageRequest.of(0, 3), 1));

        RecipeSearchService.Result result = service.findRecipesContainingAll(List.of(egg), 1);

        assertTrue(result.ingredientCountValid());
        assertEquals(List.of(omelette), result.recipes());   // Recipe has no equals() -> identity compare, fine
        assertEquals(1, result.totalPages());
        assertEquals(1L, result.totalResults());
    }

    @Test
    void twoIngredients_returnsOnlyRecipesContainingBoth() {
        Ingredient tomato = new Ingredient();
        Ingredient basil = new Ingredient();
        Recipe pasta = new Recipe();   // both
        Recipe salad = new Recipe();   // tomato only
        Recipe pesto = new Recipe();   // basil only

        when(recipeIngredientService.findAllRecipeIngredientByIngredient(tomato))
                .thenReturn(List.of(ri(pasta), ri(salad)));
        when(recipeIngredientService.findAllRecipeIngredientByIngredient(basil))
                .thenReturn(List.of(ri(pasta), ri(pesto)));

        RecipeSearchService.Result result = service.findRecipesContainingAll(List.of(tomato, basil), 1);

        assertTrue(result.ingredientCountValid());
        assertEquals(List.of(pasta), result.recipes());
    }

    private static RecipeIngredient ri(Recipe recipe) {
        RecipeIngredient ri = new RecipeIngredient();
        ri.setRecipe(recipe);
        return ri;
    }
}