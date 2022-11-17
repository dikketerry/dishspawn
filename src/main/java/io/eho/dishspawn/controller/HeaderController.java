package io.eho.dishspawn.controller;

import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {

    private ChefService chefService;

    @Autowired
    public HeaderController (ChefService chefService) {
        this.chefService = chefService;
    }



}
