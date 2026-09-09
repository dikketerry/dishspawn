package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeSearchServiceImpl implements RecipeSearchService {

    private static final int PAGE_SIZE = 3;

    private final RecipeIngredientService recipeIngredientService;

    @Override
    public Result findRecipesContainingAll(List<Ingredient> ingredients, int pageNr) {
        int count = ingredients.size();

        if (count <= 0 || count > 3) {
            return new Result(List.of(), 0, 0L, false);
        }

        if (count == 1) {
            Page<RecipeIngredient> page =
                    recipeIngredientService.findPageRecipeIngredientsByIngredient(ingredients.get(0), pageNr);
            List<Recipe> recipes = new ArrayList<>();
            for (RecipeIngredient ri : page.getContent()) {
                recipes.add(ri.getRecipe());
            }
            return new Result(recipes, page.getTotalPages(), page.getTotalElements(), true);
        }

        // 2 or 3 ingredients: intersect the per-ingredient recipe lists, then page in memory
        List<Recipe> intersection = recipesFor(ingredients.get(0));
        for (int i = 1; i < count; i++) {
            intersection = intersect(intersection, recipesFor(ingredients.get(i)));
        }

        PagedListHolder<Recipe> page = new PagedListHolder<>(intersection);
        page.setPageSize(PAGE_SIZE);
        page.setPage(pageNr - 1);
        return new Result(page.getPageList(), page.getPageCount(), intersection.size(), true);
    }

    private List<Recipe> recipesFor(Ingredient ingredient) {
        List<Recipe> recipes = new ArrayList<>();
        for (RecipeIngredient ri : recipeIngredientService.findAllRecipeIngredientByIngredient(ingredient)) {
            recipes.add(ri.getRecipe());
        }
        return recipes;
    }

    private List<Recipe> intersect(List<Recipe> a, List<Recipe> b) {
        return a.stream()
                .distinct()
                .filter(b::contains)
                .collect(Collectors.toList());
    }
}