package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;

import java.util.List;

public interface RecipeSearchService {

    /**
     * Recipes that contain ALL of the given ingredients (1–3 supported today), paged.
     *
     * @param ingredients the chosen ingredients from the spawn basket
     * @param pageNr      1-based page number
     */
    Result findRecipesContainingAll(List<Ingredient> ingredients, int pageNr);

    /**
     * @param recipes              the requested page of matching recipes (empty if none / invalid input)
     * @param totalPages           number of pages available
     * @param totalResults         total match count
     * @param ingredientCountValid false when fewer than 1 or more than 3 ingredients were supplied
     */
    record Result(List<Recipe> recipes, int totalPages, long totalResults, boolean ingredientCountValid) {};

}