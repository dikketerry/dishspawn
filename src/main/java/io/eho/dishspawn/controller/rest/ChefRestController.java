package io.eho.dishspawn.controller.rest;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Role;
import io.eho.dishspawn.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chef")
public class ChefRestController {

    private ChefService chefService;

    @Autowired
    public ChefRestController(ChefService chefService) {
        this.chefService = chefService;
    }

    // using below for security testing what i get back. is the role name in
    @GetMapping("/{chefId}")
    public Chef detailsChef(@PathVariable Long chefId) {
        return chefService.findChefById(chefId);
    }

    @GetMapping("/all")
    public List<Chef> getAllChefs()
    {
        return chefService.findAllChefs();
    }

}
