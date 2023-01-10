package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final ChefService chefService;
    private final RecipeService recipeService;

    @Autowired
    public SearchController(ChefService chefService, RecipeService recipeService) {
        this.chefService = chefService;
        this.recipeService = recipeService;
    }

    @GetMapping("/search")
    public String searchFunction(@RequestParam(value = "searchKey")String searchKey, Model model) {
        List<Chef> chefs = chefService.findAllChefByUserNameContaining(searchKey);
        List<Recipe> recipes = recipeService.findAllRecipeByNameContaining(searchKey);

        model.addAttribute("searchChefResult", chefs);
        model.addAttribute("searchRecipeResult", recipes);

        return "search-result";
    }

}
