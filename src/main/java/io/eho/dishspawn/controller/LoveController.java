package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.LoveService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoveController {
    private LoveService loveService;
    private VisualService visualService;
    private ChefService chefService;

    @Autowired
    public LoveController(LoveService loveService, VisualService visualService, ChefService chefService) {
        this.loveService = loveService;
        this.visualService = visualService;
        this.chefService = chefService;
    }

    @GetMapping("")
    public String giveLove(@PathVariable Long visualId) {

        Visual visual = visualService.findVisualById(visualId);
        Chef chef = chefService.findChefById(17l); // todo security

        if ( !loveService.chefLovedVisual(visual, chef) ) {
            loveService.saveLove(visual, chef);
        } else loveService.deleteLove(visual, chef);

        return "redirect:./";
    }

}
