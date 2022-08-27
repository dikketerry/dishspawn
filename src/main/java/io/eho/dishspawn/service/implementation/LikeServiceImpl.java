package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Like;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.repository.ChefRepository;
import io.eho.dishspawn.repository.LikeRepository;
import io.eho.dishspawn.repository.RecipeRepository;
import io.eho.dishspawn.service.LikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void saveLike(Visual visual, Chef chef) {
        Like like = new Like();
        like.setVisual(visual);
        like.setChef(chef);
        likeRepository.save(like);
    }

    @Override
    public void deleteLike(Visual visual, Chef chef) {
        Like like = likeRepository.findLikeByVisualAndChef(visual, chef);
        likeRepository.delete(like);
    }

    @Override
    public int getCountOfLikesForVisual(Visual visual) {
        List<Like> likesForVisual =
                likeRepository.findAllByVisual(visual);
        int likeCount = likesForVisual.size();
        return likeCount;
    }

    @Override
    public Boolean chefLikedVisual(Visual visual, Chef chef) {
        Like like = likeRepository.findLikeByVisualAndChef(visual, chef);

        if (like == null) {
            return false;
        } else return true;
    }
}
