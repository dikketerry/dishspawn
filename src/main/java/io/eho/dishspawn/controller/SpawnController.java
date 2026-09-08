package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.utils.Parser;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spawn")
public class SpawnController {

    private final IngredientService ingredientService;
    private final RecipeIngredientService recipeIngredientService;

    // per-session workflow state (session-scoped proxy: one instance per HTTP session)
    private final SpawnBasket basket;

    @Autowired
    public SpawnController(IngredientService ingredientService,
                           RecipeIngredientService recipeIngredientService,
                           SpawnBasket basket) {
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
        this.basket = basket;
    }

    @GetMapping("")
    public String spawnGet(Model model) {
        model.addAttribute("searchKey", basket.getSearchKey());
        model.addAttribute("ingredientSpawnSet", basket.getIngredientSpawnList());
        model.addAttribute("recipeList", basket.getRecipeSpawnList());
        model.addAttribute("ingredientListPage", basket.getIngredientListPage());
        model.addAttribute("totalFoundIngredientListPages", basket.getTotalFoundIngredientPages());
        model.addAttribute("totalFoundIngredients", basket.getTotalFoundIngredients());
        model.addAttribute("totalFoundRecipeIngredientPages", basket.getTotalFoundRecipeIngredientPages());
        model.addAttribute("totalFoundRecipeIngredients", basket.getTotalFoundRecipeIngredients());
        model.addAttribute("findRecipe", basket.isFindRecipeMethodIsUsed());
        model.addAttribute("noIngredientMessage", basket.getNoIngredientMessage());
        model.addAttribute("noRecipeMessage", basket.getNoRecipeMessage());
        model.addAttribute("incorrectIngredientsAmountMessage", basket.getIncorrectIngredientsAmountMessage());

        return "spawn-i";
    }

    @GetMapping("/search")
    public String searchIngredient(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "pageNr", required = false, defaultValue = "1") int searchPageNr) {

        basket.setSearchKey(searchKey);
        basket.setNoIngredientMessage("");

        Page<Ingredient> ingredientPage =
                ingredientService.findPageIngredientsByNameContaining(searchKey, searchPageNr);
        basket.setIngredientListPage(ingredientPage.getContent());
        basket.setTotalFoundIngredientPages(ingredientPage.getTotalPages());
        basket.setTotalFoundIngredients(ingredientPage.getTotalElements());
        noIngredientsFoundMessage(ingredientPage.getContent());

        return "redirect:/spawn";
    }

    @GetMapping("/add/{id}") // TODO refactor to exception / 4xx page
    public String addIngredientToSpawn(@PathVariable String id, Model model) {

        Long idLong = Parser.convertStringIdToLong(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idLong == 0L) {
            model.addAttribute("error", id + " is not a numeric format");
            return "error-page";
        }

        Ingredient ingredientDB = checkIngredientIdExists(idLong);
        basket.getIngredientSpawnList().add(ingredientDB);

        return "redirect:/spawn";
    }

    @GetMapping("/findrecipes")
    public String findRecipes(
            @RequestParam(value = "pageNr", required = false, defaultValue = "1") int searchPageNr) {

        // clear old recipe-list, ingredient-search
        basket.resetRecipeList();
        basket.resetIngredientSearch();

        basket.setFindRecipeMethodIsUsed(true);
        int selectedIngredientListSize = basket.getIngredientSpawnList().size();

        // check if amount of ingredients is within range
        if (selectedIngredientListSize <= 0 || selectedIngredientListSize > 3) {
            basket.setIncorrectIngredientsAmountMessage("Please select min 1, max 3 ingredient(s) for spawn");
        }

        // todo move to service layer
        else if (selectedIngredientListSize == 1) {
            Ingredient i1 = basket.getIngredientSpawnList().get(0);

            Page<RecipeIngredient> recipeIngredientPage =
                    recipeIngredientService.findPageRecipeIngredientsByIngredient(i1, searchPageNr);
            basket.setTotalFoundRecipeIngredientPages(recipeIngredientPage.getTotalPages());
            basket.setTotalFoundRecipeIngredients(recipeIngredientPage.getTotalElements());

            List<Recipe> recipes = new ArrayList<>();
            for (RecipeIngredient ri : recipeIngredientPage.getContent()) {
                recipes.add(ri.getRecipe());
            }
            basket.setRecipeSpawnList(recipes);
            noRecipesFoundMessage(recipes);
        }

        else if (selectedIngredientListSize == 2) {
            Ingredient i1 = basket.getIngredientSpawnList().get(0);
            Ingredient i2 = basket.getIngredientSpawnList().get(1);

            List<Recipe> recipeList1 = createRecipeList(i1); // see help-method
            List<Recipe> recipeList2 = createRecipeList(i2);

            List<Recipe> intersectionRecipes = createIntersectionRecipes(recipeList1, recipeList2);

            basket.setRecipeSpawnList(createPageRecipesSpawnList(intersectionRecipes, searchPageNr));
            noRecipesFoundMessage(basket.getRecipeSpawnList());
        }

        else if (selectedIngredientListSize == 3) {
            Ingredient i1 = basket.getIngredientSpawnList().get(0);
            Ingredient i2 = basket.getIngredientSpawnList().get(1);
            Ingredient i3 = basket.getIngredientSpawnList().get(2);

            List<Recipe> recipeList1 = createRecipeList(i1); // see help-method
            List<Recipe> recipeList2 = createRecipeList(i2);
            List<Recipe> recipeList3 = createRecipeList(i3);

            List<Recipe> intermediateIntersectionRecipes = createIntersectionRecipes(recipeList1, recipeList2);
            List<Recipe> intersectionRecipes = createIntersectionRecipes(intermediateIntersectionRecipes, recipeList3);

            basket.setRecipeSpawnList(createPageRecipesSpawnList(intersectionRecipes, searchPageNr));
            noRecipesFoundMessage(basket.getRecipeSpawnList());
        }

        return "redirect:/spawn";
    }

    @GetMapping("/reset")
    public String reset() {
        basket.reset();
        return "redirect:/spawn";
    }

    // private helpers below
    private void noRecipesFoundMessage(List<Recipe> recipeList) {
        if (recipeList.isEmpty()) {
            basket.setNoRecipeMessage("No recipes found for selection of ingredients. Try again.");
        }
    }

    private void noIngredientsFoundMessage(List<Ingredient> ingredientList) {
        if (ingredientList.isEmpty()) {
            basket.setNoIngredientMessage("No ingredients found for search term. Try something else.");
        }
    }

    private List<Recipe> createIntersectionRecipes(List<Recipe> recipeList1, List<Recipe> recipeList2) {
        return recipeList1.stream()
                .distinct()
                .filter(recipeList2::contains)
                .collect(Collectors.toList());
    }

    private List<Recipe> createPageRecipesSpawnList(List<Recipe> intersectionRecipes, int searchPageNr) {
        PagedListHolder<Recipe> page = new PagedListHolder<>(intersectionRecipes);
        page.setPageSize(3);
        page.setPage(searchPageNr - 1);

        basket.setTotalFoundRecipeIngredientPages(page.getPageCount());
        return page.getPageList();
    }

    private List<Recipe> createRecipeList(Ingredient ingredient) {
        List<RecipeIngredient> recipeIngredientList =
                recipeIngredientService.findAllRecipeIngredientByIngredient(ingredient);
        List<Recipe> recipeList = new ArrayList<>();
        for (RecipeIngredient ri : recipeIngredientList) {
            recipeList.add(ri.getRecipe());
        }
        return recipeList;
    }

    // todo: improve
    private Ingredient checkIngredientIdExists(Long id) {
        try {
            return ingredientService.findIngredientById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }
}