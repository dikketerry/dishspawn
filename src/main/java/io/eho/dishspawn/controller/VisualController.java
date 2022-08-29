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

import java.util.List;

@Controller
public class VisualController {

    private VisualService visualService;

//    private Visual latestVisual;

    @Autowired
    public VisualController(VisualService visualService) {
        this.visualService = visualService;
    }

    @GetMapping("/home/page/{pageNr}")
    @Primary
    public String getVisualsPaged(@PathVariable("pageNr") int currentPage,
                                  Model model) {

        // get latest visual
        Visual latestVisual = visualService.findLatestVisual();
//        List<Visual> allVisuals = visualService.findAllVisuals();

        // get all visuals paginated - ensure the first visual is not shown
        // in the view
        Page<Visual> visualPage = visualService.findPageVisuals(currentPage);

        List<Visual> pagedVisuals;
//        if (currentPage == 1) {
//            pagedVisuals = visualPage.getContent();
//            pagedVisuals.remove(0);
//        } else {
//            pagedVisuals = visualPage.getContent();
//        }

        int totalPages = visualPage.getTotalPages();
        long totalVisuals = visualPage.getTotalElements();
        pagedVisuals = visualPage.getContent();

        model.addAttribute("latestVisual", latestVisual);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalVisuals", totalVisuals);
        model.addAttribute("pagedVisuals", pagedVisuals);

        // diagnostic print
        for (Visual visual : pagedVisuals) {
            System.out.println(visual);
        }

        return "home";
    }

}
