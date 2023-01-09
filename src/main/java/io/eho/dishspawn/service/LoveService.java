package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;

public interface LoveService {

    void saveLove(Visual visual, Chef chef);
    void deleteLove(Visual visual, Chef chef);
    Boolean chefLovedVisual(Visual visual, Chef chef);

}
