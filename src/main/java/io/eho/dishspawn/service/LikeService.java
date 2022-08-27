package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Like;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface LikeService {

    void saveLike(Visual visual, Chef chef);
    void deleteLike(Visual visual, Chef chef);
    Boolean chefLikedVisual(Visual visual, Chef chef);
    int getCountOfLikesForVisual(Visual visual);


}
