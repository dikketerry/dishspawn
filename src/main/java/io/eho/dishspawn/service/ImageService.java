package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;

public interface ImageService {

    String generateImage(Recipe recipe);
    Visual saveVisual(Recipe recipe, Long newId);
}
