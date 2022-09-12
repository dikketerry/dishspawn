package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.util.List;

public interface VisualService {

    Long findNextIdValue();
    void saveVisual(Visual visual);
    void deleteVisual(Visual visual);
    List<Visual> findAllVisuals();
    List<Visual> findLast200Visuals();
    List<Visual> findAllVisualsForRecipe();
    Visual findLatestVisual();
    Page<Visual> findPageVisuals(int currentPage);
    Visual findVisualById(Long id);
    List<Visual> findAllVisualsForChef(Chef chef);
    List<Visual> findLast200VisualsForChef(Chef chef);
    Visual findLatestVisualForChef(Chef chef);

    Visual findLastVisualForRecipe(Recipe recipe);
//    Page<Visual> findPageVisualsForChef(Chef chef);
}
