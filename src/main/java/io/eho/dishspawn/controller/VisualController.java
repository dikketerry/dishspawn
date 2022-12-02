package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VisualController {

    private VisualService visualService;

    @Autowired
    public VisualController(VisualService visualService) {
        this.visualService = visualService;
    }

    @GetMapping("/visual")
    public String showRecipe(@RequestParam Long visualId, Model model) {

        Visual visual = visualService.findVisualById(visualId);
        System.out.println("file location: " + visual.getFileLocation());
        model.addAttribute("visual", visual);

        return "visual";
    }

}
