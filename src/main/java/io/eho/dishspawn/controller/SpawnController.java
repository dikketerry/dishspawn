package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.utils.Parser;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeSearchService;
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
    private final RecipeSearchService recipeSearchService;
    // per-session workflow state (session-scoped proxy: one instance per HTTP session)
    private final SpawnBasket basket;

    @Autowired
    public SpawnController(IngredientService ingredientService,
                           RecipeSearchService recipeSearchService,
                           SpawnBasket basket) {
        this.ingredientService = ingredientService;
        this.recipeSearchService = recipeSearchService;
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
        RecipeSearchService.Result result = recipeSearchService.findRecipesContainingAll(basket.getIngredientSpawnList(), searchPageNr);

        if (!result.ingredientCountValid()) {
            basket.setIncorrectIngredientsAmountMessage("Please select min 1, max 3 ingredient(s) for spawn");
            return "redirect:/spawn";
        }

        basket.setRecipeSpawnList(result.recipes());
        basket.setTotalFoundRecipeIngredientPages(result.totalPages());
        basket.setTotalFoundRecipeIngredients(result.totalResults());

        if (result.recipes().isEmpty()) {
            basket.setNoRecipeMessage("No recipes found for selection of ingredients. Try again.");
        }

        return "redirect:/spawn";
    }

    @GetMapping("/reset")
    public String reset() {
        basket.reset();
        return "redirect:/spawn";
    }

    // ------------ private helpers

    private void noIngredientsFoundMessage(List<Ingredient> ingredientList) {
        if (ingredientList.isEmpty()) {
            basket.setNoIngredientMessage("No ingredients found for search term. Try something else.");
        }
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