package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/spawn")
public class SpawnController {

    private IngredientService ingredientService;
    private RecipeService recipeService;

    private Set<Ingredient> ingredientSpawnSet = new HashSet<>();
    private List<Recipe> recipeSpawnList = new ArrayList<>();
    private List<Ingredient> ingredientListPage = new ArrayList<>();

    private int totalFoundIngredientListPages;
    private long totalFoundIngredients;

    private StringBuilder searchKey = new StringBuilder();

    // dependency injection via constructor
    public SpawnController() { }

    @Autowired
    public SpawnController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("")
    public String spawnGet(Model model) {

        // null check on searchKey to avoid NPE when launching spawn page without a searchKey
        if (searchKey != null) {
            model.addAttribute("searchKey", searchKey.toString());
        }

        model.addAttribute("ingredientSpawnSet", ingredientSpawnSet);
        model.addAttribute("recipeList", recipeSpawnList);
        model.addAttribute("ingredientListPage", ingredientListPage);
        model.addAttribute("totalFoundIngredientListPages", totalFoundIngredientListPages);
        model.addAttribute("totalFoundIngredients", totalFoundIngredients);

        return "spawn-i";
    }

    @GetMapping("/search")
    public String searchIngredient(
            @RequestParam(value="searchKey", required=false, defaultValue = "")String searchKey,
            @RequestParam(value="pageNr", required=false, defaultValue="1")int searchPageNr) {

        // clean up searchKey StringBuilder, as each new search should give a clean search term
        this.searchKey.setLength(0);

        // store provided param searchKey in the newly created fresh StringBuilder
        this.searchKey.append(searchKey);

        // paged search results
        Page ingredientPage = ingredientService.findPageIngredientsByNameContaining(searchKey, searchPageNr);

        // convert page result to global search-ingredient-result list
        ingredientListPage = ingredientPage.getContent();

        // get the total # of pages
        totalFoundIngredientListPages = ingredientPage.getTotalPages();

        // get the total # of elements
        totalFoundIngredients = ingredientPage.getTotalElements();

        return "redirect:/spawn";
    }

    @GetMapping("/add/{id}")
    public String addIngredientToSpawn(@PathVariable String id, Model model) {

        Long idLong = convertStringIdToLong(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Ingredient ingredientDB = checkIngredientIdExists(idLong);

        // silly if here, ingredient was found in DB so will never be null..., but hey anyway
        if (ingredientDB == null) {
            String idNotExist = "no ingredient with id: " + idLong;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        ingredientSpawnSet.add(ingredientDB);   // add found ingredient

        return "redirect:/spawn";
    }

    @GetMapping("/findrecipes")
    public String findRecipes(Model model) {
        // before finding new recipes, first clear old list recipe and search
        resetRecipeList();
        resetIngredientSearch();

        // for each ingredient in selection, create its recipe-ingredients array
        for (Ingredient ingredient : ingredientSpawnSet) {
            RecipeIngredient[] recipeIngredientArr =
                    ingredient.getRecipeIngredients().toArray(
                            new RecipeIngredient[0]);

            // loop through each recipe-ingredient array, get per r-i the
            // belonging recipe and add to recipe-spawn-list
            for (int i = 0; i < recipeIngredientArr.length; i++) {
                Recipe recipe = recipeIngredientArr[i].getRecipe();
                recipeSpawnList.add(recipe);
            }
        }
        // TODO: only recipes which have ALL ingredients should be shown
        model.addAttribute("recipeList", recipeSpawnList);

        return "redirect:/spawn";
    }

    @PostMapping("/spawn/{id}")
    public String spawnGo(@PathVariable String id, Model model) {
        // help method to convert String to Long and catch non-numerical input
        Long idLong = convertStringIdToLong(id);

        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Recipe recipe = recipeService.findRecipeById(idLong);
        model.addAttribute(recipe);

        return "spawn-o";
    }

    @GetMapping("/reset")
    public String reset() {
        resetIngredientSearch();
        ingredientSpawnSet.clear();
        resetRecipeList();
        return "redirect:/spawn";
    }

    // private helpers below
    private void resetIngredientSearch() {
        // initiate new IngredientListPage
        ingredientListPage = new ArrayList<>();
        // clean the StringBuilder 'searchKey'
        this.searchKey.setLength(0);
    }

    private void resetRecipeList() {
        recipeSpawnList.clear();
    }


    private Long convertStringIdToLong(String id) {
        Long idLong;

        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0l;
        }

        return idLong;
    }

    private Ingredient checkIngredientIdExists(Long id) {
        Ingredient ingredientDB;

        try {
            ingredientDB = ingredientService.findIngredientById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return ingredientDB;
    }

}
