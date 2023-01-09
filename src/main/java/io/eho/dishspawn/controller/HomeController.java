package io.eho.dishspawn.controller;

import io.eho.dishspawn.controller.util.ListSkipper;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.LoveService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final VisualService visualService;
    private final LoveService loveService;
    private final ChefService chefService;

    private int totalFoundVisualPages;

    @Autowired
    public HomeController(VisualService visualService,
                          LoveService loveService,
                          ChefService chefService) {
        this.visualService = visualService;
        this.loveService = loveService;
        this.chefService = chefService;
    }

    @GetMapping()
    public String getVisualsPaged(@RequestParam(value="pageNr", required=false, defaultValue="1")int searchPageNr,
            Model model) {

        // not used - but might be useful at some point
        Boolean loggedIn = false;

        Visual latestVisual = visualService.findLatestVisual();
        Chef chef = null;

        // check if authenticated or anonymous
        try {
            String currentUserName = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            chef = chefService.findChefByUserName(currentUserName);

            if (!currentUserName.equals("anonymousUser")) {
                loggedIn = true;
                latestVisual.setUserLove(loveService.chefLovedVisual(latestVisual, chef));
            }
        } catch (Exception e) {
            System.out.println("User not logged in.");
        }

        // get most recent 200 visuals minus most recent
        List<Visual> mostRecent200Visuals = visualService.findLast200Visuals();
//        List<Visual> mostRecent200MinusFirst = last200MinusFirst(mostRecent200Visuals); // remove 1 visual
        List<Visual> mostRecent200MinusFirst = ListSkipper.skipFirst(mostRecent200Visuals);

        // assign love boolean per visual
        for (Visual v : mostRecent200MinusFirst) {
            v.setUserLove(loveService.chefLovedVisual(v, chef));
        }

        // page visuals
        List<Visual> pageVisuals = createPageVisuals(mostRecent200MinusFirst, searchPageNr); // page it!

        // add stuff to the model for handling in the web page
        model.addAttribute("latestVisual", latestVisual);
        model.addAttribute("totalPages", totalFoundVisualPages);
        model.addAttribute("pagedVisuals", pageVisuals);
        model.addAttribute("chef", chef);

        return "home";
    }

//     helper to create paged content - todo: investigate how to place this in a util class - issue is i need to set
//      a nr. of pages AND return a paged list.
    private List<Visual> createPageVisuals(List<Visual> visuals, int searchPageNr) {

        PagedListHolder page = new PagedListHolder(visuals);
        page.setPageSize(3);
        page.setPage(searchPageNr - 1);

        totalFoundVisualPages = page.getPageCount();
        List<Visual> visualListPage = page.getPageList();

        return visualListPage;
    }

}
