package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VisualController {

    private VisualService visualService;

    private Visual latestVisual;

    @Autowired
    public VisualController(VisualService visualService) {
        this.visualService = visualService;
    }

    @GetMapping("/home2/page/{pageNr}")
    @Primary
    public String getVisualsPaged(@PathVariable("pageNr") int currentPage, Model model) {

        // get latest visual
        latestVisual = visualService.findLatestVisual();

        // get all visuals paginated - ensure the first visual is not shown
        // in the view
        Page<Visual> visualPage = visualService.findPage(currentPage);
        int totalPages = visualPage.getTotalPages();
        long totalVisuals = visualPage.getTotalElements();
        List<Visual> visuals = visualPage.getContent();

        model.addAttribute("latestVisual", latestVisual);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalVisuals", totalVisuals);
        model.addAttribute("visuals", totalVisuals);

        return "home2";

    }


}
