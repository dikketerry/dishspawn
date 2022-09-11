package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.Parser;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.RecipeIngredient;
import io.eho.dishspawn.service.IngredientService;
import io.eho.dishspawn.service.RecipeIngredientService;
import io.eho.dishspawn.service.RecipeService;
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

    private IngredientService ingredientService;
    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;

    // model-attribute vars are defined globally to enable returning always the same page
    private List<Ingredient> ingredientSpawnList = new ArrayList<>();
    private List<Recipe> recipeSpawnList = new ArrayList<>();
    private List<Ingredient> ingredientListPage = new ArrayList<>();

    // vars for paging of search results ingredients / recipes
    private int totalFoundIngredientPages;
    private int totalFoundRecipeIngredientPages;
    private long totalFoundIngredients;
    private long totalFoundRecipeIngredients;

    // boolean for show/hide search recipe result container
    private boolean findRecipeIsUsed;

    // StringBuilder vars for storing searchKey ingredient search and message for recipe search
    private StringBuilder searchKey = new StringBuilder();
    private StringBuilder message = new StringBuilder();

    // dependency injection via constructor
    public SpawnController() { }

    @Autowired
    public SpawnController(IngredientService ingredientService, RecipeService recipeService,
                           RecipeIngredientService recipeIngredientService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
    }

    @GetMapping("")
    public String spawnGet(Model model) {

        // null check on searchKey to avoid NPE when launching spawn page without a searchKey
        if (searchKey != null) {
            model.addAttribute("searchKey", searchKey.toString());
        }

        model.addAttribute("ingredientSpawnSet", ingredientSpawnList);
        model.addAttribute("recipeList", recipeSpawnList);
        model.addAttribute("ingredientListPage", ingredientListPage);
        model.addAttribute("totalFoundIngredientListPages", totalFoundIngredientPages);
        model.addAttribute("totalFoundIngredients", totalFoundIngredients);
        model.addAttribute("totalFoundRecipeIngredientPages", totalFoundRecipeIngredientPages);
        model.addAttribute("totalFoundRecipeIngredients", totalFoundRecipeIngredients);
        model.addAttribute("findRecipe", findRecipeIsUsed);
        model.addAttribute("message", message.toString());

        return "spawn-i";
    }

    @GetMapping("/search")
    public String searchIngredient(
            @RequestParam(value="searchKey", required=false, defaultValue = "")String searchKey,
            @RequestParam(value="pageNr", required=false, defaultValue="1")int searchPageNr) {

        this.searchKey.setLength(0); // clean up searchKey StringBuilder (a new search should start clean)
        this.searchKey.append(searchKey); // store provided searchKey in the fresh StringBuilder

        // paged search results ingredients
        Page ingredientPage = ingredientService.findPageIngredientsByNameContaining(searchKey, searchPageNr);
        ingredientListPage = ingredientPage.getContent();
        totalFoundIngredientPages = ingredientPage.getTotalPages();
        totalFoundIngredients = ingredientPage.getTotalElements();

        return "redirect:/spawn";
    }

    @GetMapping("/add/{id}")
    public String addIngredientToSpawn(@PathVariable String id, Model model) {

        Long idLong = Parser.convertStringIdToLong(id);

        // help method returns 0 if conversion to nr. didn't work
        if (idLong == 0l) {
            String noNumber = id + " is not a numeric format";
            model.addAttribute("error", noNumber);
            return "error-page";
        }

        Ingredient ingredientDB = checkIngredientIdExists(idLong);

        // silly if check here, ingredient was found in DB so will never be null..., but hey anyway
        if (ingredientDB == null) {
            String idNotExist = "no ingredient with id: " + idLong;
            model.addAttribute("error", idNotExist);
            return "error-page";
        }

        ingredientSpawnList.add(ingredientDB);

        return "redirect:/spawn";
    }

    @GetMapping("/findrecipes")
    public String findRecipes(
            @RequestParam(value="pageNr", required=false, defaultValue="1")int searchPageNr) {

        // clear old recipe-list and ingredient-search
        resetRecipeList();
        resetIngredientSearch();

        findRecipeIsUsed = true; // use of the find recipe function to 'true'
        int selectedIngredientListSize = ingredientSpawnList.size(); // size is needed in following if statements

        if (selectedIngredientListSize == 0)
        {
            message.append("please select ingredient(s) for spawn");
        }

        else if (selectedIngredientListSize == 1) // 1 ingredient selected
        {
            Ingredient i1 = ingredientSpawnList.get(0);

            // create page recipe-ingredients
            Page<RecipeIngredient> recipeIngredientPage =
                    recipeIngredientService.findPageRecipeIngredientsByIngredient(i1, searchPageNr);
            totalFoundRecipeIngredientPages = recipeIngredientPage.getTotalPages();
            totalFoundRecipeIngredients = recipeIngredientPage.getTotalElements();
            List<RecipeIngredient> recipeIngredientList = recipeIngredientPage.getContent();

            // add the paged recipes to the recipe spawn list
            for (RecipeIngredient ri : recipeIngredientList) {
                recipeSpawnList.add(ri.getRecipe());
            }
            noRecipesFoundMessage(recipeSpawnList);
        }

        else if (selectedIngredientListSize == 2)
        {
            Ingredient i1 = ingredientSpawnList.get(0);
            Ingredient i2 = ingredientSpawnList.get(1);

            // get recipes with recipeIngredient.ingredient(i1) AND recipeIngredient.ingredient(i2)
            List<Recipe> recipeList1 = createRecipeList(i1); // see help-method
            List<Recipe> recipeList2 = createRecipeList(i2);

            // create intersection of 2 lists
            List<Recipe> intersectionRecipes = createIntersectionRecipes(recipeList1, recipeList2);

            // page the intersection result, assign it to recipeSpawnList
            recipeSpawnList = createPageRecipesSpawnList(intersectionRecipes, searchPageNr);
            noRecipesFoundMessage(recipeSpawnList);
        }

        else if (selectedIngredientListSize == 3)
        {
            Ingredient i1 = ingredientSpawnList.get(0);
            Ingredient i2 = ingredientSpawnList.get(1);
            Ingredient i3 = ingredientSpawnList.get(2);

            // get recipes with recipeIngredient.ingredient(i1) AND recipeIngredient.ingredient(i2)
            List<Recipe> recipeList1 = createRecipeList(i1); // see help-method
            List<Recipe> recipeList2 = createRecipeList(i2);
            List<Recipe> recipeList3 = createRecipeList(i3);

            // create intersection of 3 lists
            List<Recipe> intermediateIntersectionRecipes = createIntersectionRecipes(recipeList1, recipeList2);
            List<Recipe> intersectionRecipes = createIntersectionRecipes(intermediateIntersectionRecipes, recipeList3);

            // page the intersection result, assign it to recipeSpawnList
            recipeSpawnList = createPageRecipesSpawnList(intersectionRecipes, searchPageNr);
            noRecipesFoundMessage(recipeSpawnList);
        }

        return "redirect:/spawn";
    }

    @GetMapping("/reset")
    public String reset() {
        resetIngredientSearch();
        ingredientSpawnList.clear();
        resetRecipeList();
        findRecipeIsUsed = false;
        return "redirect:/spawn";
    }

    // private helpers below
    private void noRecipesFoundMessage(List<Recipe> recipeList) {
        if (recipeList.size() == 0) {
            message.append("no recipes found for selection of ingredients");
        }
    }

    private List createIntersectionRecipes(List<Recipe> recipeList1, List<Recipe> recipeList2) {
        List<Recipe> intersection = recipeList1.stream()
                .distinct()
                .filter(recipeList2::contains)
                .collect(Collectors.toList());
        return intersection;
    }

    private List createPageRecipesSpawnList(List<Recipe> intersectionRecipes, int searchPageNr) {
        PagedListHolder page = new PagedListHolder(intersectionRecipes);
        page.setPageSize(3);
        page.setPage(searchPageNr - 1);

        totalFoundRecipeIngredientPages = page.getPageCount();
        recipeSpawnList = page.getPageList();

        return recipeSpawnList;
    }

    private List createRecipeList(Ingredient ingredient) {
        // todo: needs a converter: List<Recipe> recipeList = recipeIngredientService.findAllRecipeByIngredientByIngredient(ingredient)
        List<RecipeIngredient> recipeIngredientList =
                recipeIngredientService.findAllRecipeIngredientByIngredient(ingredient);

        List<Recipe> recipeList = new ArrayList<>();

        for (RecipeIngredient ri : recipeIngredientList) {
            Recipe r = ri.getRecipe();
            recipeList.add(r);
        }

        return recipeList;
    }

    private void resetIngredientSearch() {
        // initiate new IngredientListPage
        ingredientListPage = new ArrayList<>();
        // clean the StringBuilder 'searchKey'
        this.searchKey.setLength(0);
    }

    private void resetRecipeList() {
        recipeSpawnList.clear();
        this.message.setLength(0);
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
