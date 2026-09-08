package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Per-user state for the multi-step "spawn" workflow: the ingredient basket, the recipe
 * results, paging counters, and the one-shot UI messages.
 *
 * Session-scoped: Spring injects a proxy into the singleton {@link SpawnController} and
 * backs it with one instance per HTTP session, so two chefs searching at the same time no
 * longer share a basket. This replaces the mutable instance fields that used to sit on
 * SpawnController.
 */
@Component
@SessionScope
@Getter
@Setter
public class SpawnBasket {

    // the ingredients the user picked, and the recipes found for them
    private List<Ingredient> ingredientSpawnList = new ArrayList<>();
    private List<Recipe> recipeSpawnList = new ArrayList<>();

    // current page of the ingredient name-search
    private List<Ingredient> ingredientListPage = new ArrayList<>();

    // paging counters / totals for the two result lists
    private int totalFoundIngredientPages;
    private int totalFoundRecipeIngredientPages;
    private long totalFoundIngredients;
    private long totalFoundRecipeIngredients;

    // whether "find recipes" has run (controls a results container in the view)
    private boolean findRecipeMethodIsUsed;

    // last ingredient search term, echoed back into the search box and the paging links
    private String searchKey = "";

    // one-shot messages; only ever one is shown at a time
    private String noRecipeMessage = "";
    private String noIngredientMessage = "";
    private String incorrectIngredientsAmountMessage = "";

    /** Full reset — the /spawn/reset action. */
    public void reset() {
        resetIngredientSearch();
        ingredientSpawnList.clear();
        resetRecipeList();
        findRecipeMethodIsUsed = false;
    }

    /** Clears the ingredient search: its page, the search term, and its messages. */
    public void resetIngredientSearch() {
        ingredientListPage = new ArrayList<>();
        searchKey = "";
        noIngredientMessage = "";
        incorrectIngredientsAmountMessage = "";
    }

    /** Clears the recipe results and their message. */
    public void resetRecipeList() {
        recipeSpawnList.clear();
        noRecipeMessage = "";
    }
}