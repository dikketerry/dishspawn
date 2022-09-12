package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chef")
public class ChefController {

    private ChefService chefService;
    private VisualService visualService;

    private int totalFoundVisualsChefPages;
    private StringBuilder message = new StringBuilder();

    public ChefController() {}

    @Autowired
    public ChefController(ChefService chefService, VisualService visualService) {
        this.chefService = chefService;
        this.visualService = visualService;
    }

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

    @GetMapping("/{chefId}")
    public String showChef(@PathVariable Long chefId,
                           @RequestParam (value="pageNr", required=false, defaultValue="1")int searchPageNr,
                           Model model) {

        Chef chef = chefService.findChefById(chefId);
        Visual latestVisualForChef = visualService.findLatestVisualForChef(chef);

        List<Visual> top200VisualsChef = visualService.findLast200VisualsForChef(chef);
        List<Visual> top200MinusFirst = last200MinusFirst(top200VisualsChef); // remove 1 visual
        List<Visual> visualsChefPage = createPageVisualsChefList(top200MinusFirst, searchPageNr);

        resetMessage();
        noSpawnsFoundCheck(top200VisualsChef);

        model.addAttribute("chef", chef);
        model.addAttribute("latestVisualForChef", latestVisualForChef);
        model.addAttribute("visualsChef", visualsChefPage);
        model.addAttribute("totalPages", totalFoundVisualsChefPages);
        model.addAttribute("message", message.toString());

        return "chef";
    }

    private List<Visual> last200MinusFirst(List<Visual> top200VisualsChef) {
        return top200VisualsChef.stream()
                .skip(1)
                .collect(Collectors.toList());
    }

    // register a new chef
    @GetMapping("/add")
    public String registerChef(Model model) {

        model.addAttribute("chef", new Chef());
        return "add-chef";
    }

    // post - save new chef
    @PostMapping("save")
    public String saveChef(@ModelAttribute("chef") Chef chef) {
//        TODO: error handling + page redirection
        chefService.saveChef(chef);
        return "redirect:all";
    }

    // private helpers below
    private void noSpawnsFoundCheck(List<Visual> visualsChef) {
        if (visualsChef.size() == 0) {
            message.append(" has not created any spawns yet has not created any spawns yet has not created any spawns" +
                                   " yet has not created any spawns yet has not created any spawns yet has not " +
                                   "created any spawns yet has not created any spawns yet has not created any spawns " +
                                   "yet has not created any spawns yet has not created any spawns yet :( ");
        }
    }

    private List createPageVisualsChefList(List<Visual> visualsChef, int searchPageNr) {
        PagedListHolder page = new PagedListHolder(visualsChef);
        page.setPageSize(3);
        page.setPage(searchPageNr - 1);

        totalFoundVisualsChefPages = page.getPageCount();
        List<Visual> visualsChefPage = page.getPageList();

        return visualsChefPage;
    }

    private void resetMessage() {
        message.setLength(0);
    }
}
