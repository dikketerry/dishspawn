package io.eho.dishspawn.controller.rest;

import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visual")
public class VisualRestController {

    private VisualService visualService;

    @Autowired
    public VisualRestController(VisualService visualService) {
        this.visualService = visualService;
    }

    // collect all visuals
    @GetMapping("/all")
    public List<Visual> getAllVisuals()
    {
        return visualService.findAllVisuals();
    }
}
