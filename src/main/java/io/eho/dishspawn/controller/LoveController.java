package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.LoveService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

    @GetMapping("/visual/{visualId}/love")
    public String handleLove(@PathVariable Long visualId, HttpServletRequest request) {

        Visual visual = visualService.findVisualById(visualId);

        String currentUserName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        System.out.println("username string: " + currentUserName);

        Chef chef = chefService.findChefByUserName(currentUserName);
        System.out.println("username: " + chef.getUserName());

        if ( !loveService.chefLovedVisual(visual, chef) ) {
            loveService.saveLove(visual, chef);
        } else loveService.deleteLove(visual, chef);

        return getPreviousPageByRequest(request).orElse("/");
    }

    // to handle navigation to previous page - TODO: make available for all controllers
    private Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }

}
