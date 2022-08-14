package io.eho.dishspawn.controller.rest;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
public class ChefRestController {

    // set ChefService property
    private ChefService chefService;

    // constructors
    public ChefRestController() { }

    @Autowired // dependency injection via constructor
    public ChefRestController(ChefService chefService) {
        this.chefService = chefService;
    }

    // method for collecting chefs
    @GetMapping("/all")
    public List<Chef> getAllChefs() {
        return chefService.getAllChefs();
    }

    // method for collecting chefs, recipes and ingredients based on search
    // input


}
