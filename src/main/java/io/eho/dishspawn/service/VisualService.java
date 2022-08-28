package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;

import java.util.List;

public interface VisualService {

    void saveVisual(Visual visual, Chef chef);
    void deleteVisual(Visual visual, Chef chef);
    List<Visual> findAllVisualsForRecipe();
    Visual findVisualById(Long id);

}
