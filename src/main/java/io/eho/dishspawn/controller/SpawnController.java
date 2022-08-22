package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/spawn")
public class SpawnController {

    // set required properties to spawn:
    // 1. ingredient service to find ingredient id's DB based on input user
    // 2. recipeingredient service to find recipeingredients with ingredient
    // ids and provide belonging recipe ids
    // 3. recipeservice to collect belonging recipes

    private IngredientService ingredientService;
    private RecipeIngredientService recipeIngredientService;
    private RecipeService recipeService;

    private Set<Ingredient> ingredientSearchResult = new HashSet<>();
    private Set<Ingredient> ingredientSpawnSet = new HashSet<>();
//    private Set<Recipe> recipeSpawnSet = new HashSet<>();
    private List<Recipe> recipeSpawnList = new ArrayList<>();

    // dependency injection via constructor
    public SpawnController() { }

    @Autowired
    public SpawnController(IngredientService ingredientService,
                           RecipeIngredientService recipeIngredientService,
                           RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("")
    public String spawnGet(Model model) {
        model.addAttribute("ingredientSearchResult", ingredientSearchResult);
        model.addAttribute("ingredientSpawnSet", ingredientSpawnSet);
        model.addAttribute("recipeList", recipeSpawnList);
        return "spawn-dummy";
    }

    @GetMapping("/search")
    public String searchIngredient(@RequestParam(value = "search",
            required = false) String search, Model model) {

        //diagnostic print
//        System.out.println("searchIngredient method being executed");
        ingredientSearchResult =
                ingredientService.getAllIngredientsByNameContaining(search);

        model.addAttribute("ingredientResult", ingredientSearchResult);

        //diagnostic print
        for (Ingredient i : ingredientSearchResult) {
            System.out.println("found: " + i.getName());
        }

//        return "spawn-dummy";
        return "redirect:/spawn";
    }

    // TODO: inefficient adding of ingredient to spawn selection -
    //  investigate how a search result can directly be bound to spawn
    //  selection, without collecting it again from the DB
    @GetMapping("/add/{id}")
    public String addIngredientToSpawn(@PathVariable String id, Model model) {

        System.out.println("AddIngredientToSpawn method processing");

        Long idLong = convertStringIdToLong(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Ingredient ingredientDB = checkIngredientIdExists(idLong);

        if (ingredientDB == null) {
            String idNotExist = "no ingredient with id: " + idLong;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        ingredientSpawnSet.add(ingredientDB);
        ingredientSearchResult.clear();

        // diagnostic prints
        for (Ingredient tempIngredient : ingredientSpawnSet) {
            System.out.println("Ingredient SpawnSet: " + tempIngredient.getName());
        }
        return "redirect:/spawn";
    }

    @GetMapping("/resetspawn")
    public String resetSpawnSet() {
        ingredientSpawnSet.clear();
        resetRecipeList();
        return "redirect:/spawn";
    }

    @GetMapping("/resetingredients")
    public String resetIngredientSearch() {
        ingredientSearchResult.clear();
        return "redirect:/spawn";
    }

    private void resetRecipeList() {
        recipeSpawnList.clear();
    }

    @GetMapping("/findrecipes")
    public String findRecipes(Model model) {
        // before finding new recipes, first clear the old list
        resetRecipeList();

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
            ingredientDB = ingredientService.getIngredientById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }

        return ingredientDB;
    }

}
