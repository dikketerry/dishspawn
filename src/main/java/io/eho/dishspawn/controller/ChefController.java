package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chef")
public class ChefController {

    // property ChefService
    private ChefService chefService;

    public ChefController() {}

    // inject ChefService property via constructor
    @Autowired
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    // set up mappings (GET, POST, etc.)
    // collect all chefs
    @GetMapping("/all")
    public String getAllChefs(Model model) {
        List<Chef> chefsFromDB = chefService.findAllChefs();

        if (chefsFromDB == null) {
            model.addAttribute("error", "no chefs found");
            return "error-page";
        }

        model.addAttribute("chefs", chefsFromDB);
        return "all-chefs";
    }

    // register a new chef
    @GetMapping("/add")
    public String registerChef(Model model) {
        model.addAttribute("chef", new Chef());
        return "add-chef";
    }

    // collect chef(s) username based on string input

    // post - save new chef
    @PostMapping("save")
    public String saveChef(@ModelAttribute("chef") Chef chef) {
//        TODO: error handling + page redirection

        chefService.saveChef(chef);
        return "redirect:all";

    }
}
