package io.eho.dishspawn.controller;

import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VisualController {

    private VisualService visualService;

    private int totalFoundVisualPages;
    private List<Visual> visualList;

    @Autowired
    public VisualController(VisualService visualService) {
        this.visualService = visualService;
    }

    @GetMapping("/home")
    public String getVisualsPaged(
            @RequestParam(value="pageNr", required=false, defaultValue="1")int searchPageNr,
            Model model) {

        // get most recent visual
        Visual latestVisual = visualService.findLatestVisual();

        // get paged list most recent 200 visuals minus most recent
        List<Visual> mostRecent200Visuals = visualService.findLast200Visuals();
        List<Visual> mostRecent200MinusFirst = last200MinusFirst(mostRecent200Visuals); // remove 1 visual
        List<Visual> pagedVisuals = createPageVisuals(mostRecent200MinusFirst, searchPageNr); // page it!

        model.addAttribute("latestVisual", latestVisual);
        model.addAttribute("totalPages", totalFoundVisualPages);
        model.addAttribute("pagedVisuals", pagedVisuals);

        return "home";
    }

    @GetMapping("/visual")
    public String showRecipe(@RequestParam Long visualId, Model model) {

        Visual visual = visualService.findVisualById(visualId);
        model.addAttribute("visual", visual);

        return "visual";
    }

    private List<Visual> createPageVisuals(List<Visual> visuals, int searchPageNr) {

        PagedListHolder page = new PagedListHolder(visuals);
        page.setPageSize(3);
        page.setPage(searchPageNr - 1);

        totalFoundVisualPages = page.getPageCount();
        visualList = page.getPageList();

        return visualList;
    }

    private List<Visual> last200MinusFirst(List<Visual> last200Visuals) {
        return last200Visuals.stream()
                .skip(1)
                .collect(Collectors.toList());
    }

}
