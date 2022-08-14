package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.IngredientService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChefController {

    // set ChefService property
    private ChefService chefService;

    // constructors
    public ChefController() { }

    @Autowired // dependency injection via constructor
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    // method for collecting chefs
    @GetMapping("/api/chefs")
    public List<Chef> getAllChefs() {
        return chefService.findAllChefs();
    }

    // method for collecting chefs, recipes and ingredients based on search
    // input


}
