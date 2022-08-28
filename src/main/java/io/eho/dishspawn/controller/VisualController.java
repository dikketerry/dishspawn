package io.eho.dishspawn.controller;

import io.eho.dishspawn.service.VisualService;
import org.springframework.stereotype.Controller;

@Controller
public class VisualController {

    private VisualService visualService;

    public VisualController(VisualService visualService) {
        this.visualService = visualService;
    }



}
