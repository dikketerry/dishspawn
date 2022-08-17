package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Ingredient;
import io.eho.dishspawn.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    // property IngredientService
    private IngredientService ingredientService;

    // dependency injection with constructor
    public IngredientController() { }

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // get method(s)
    // collect all ingredients
    @GetMapping("/all")
    public String getAllIngredients(Model model) {
        List<Ingredient> ingredientsFromDB = ingredientService.getAllIngredients();

        if (ingredientsFromDB == null) {
            model.addAttribute("error", "no ingredients found");
            return "error-page";
        }

        model.addAttribute("ingredients", ingredientsFromDB);
        return "all-ingredients";
    }

    // add a new ingredient
    @GetMapping("/add")
    public String addIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "add-ingredient";
    }

    // collect ingredients based on string input



    // post method(s)
    // save ingredient
    @PostMapping("save")
    public String saveIngredient(@ModelAttribute("ingredient") Ingredient ingredient) {
//        TODO: error handling + page redirection

        ingredientService.saveIngredient(ingredient);
        return "redirect:all";

    }
}
