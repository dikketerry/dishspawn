package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface VisualService {

    void saveVisual(Visual visual, Chef chef);
    void deleteVisual(Visual visual, Chef chef);
    List<Visual> findAllVisuals();
    List<Visual> findLast200Visuals();
    List<Visual> findAllVisualsForRecipe();
    Visual findLatestVisual();
    Page<Visual> findPage(int currentPage);
    Visual findVisualById(Long id);


}
