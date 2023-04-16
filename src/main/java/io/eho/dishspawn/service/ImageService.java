package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Recipe;
import org.springframework.stereotype.Service;

public interface ImageService {

    public String generateImage(Recipe recipe);
}
