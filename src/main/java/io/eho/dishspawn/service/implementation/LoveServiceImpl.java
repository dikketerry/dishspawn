package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Love;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.repository.LoveRepository;
import io.eho.dishspawn.service.LoveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoveServiceImpl implements LoveService {

    private LoveRepository loveRepository;

    public LoveServiceImpl(LoveRepository loveRepository) {
        this.loveRepository = loveRepository;
    }

    @Override
    public void saveLove(Visual visual, Chef chef) {
        Love love = new Love();
        love.setVisual(visual);
        love.setChef(chef);
        loveRepository.save(love);
    }

    @Override
    public void deleteLove(Visual visual, Chef chef) {
        Love love = loveRepository.findLoveByVisualAndChef(visual, chef);
        loveRepository.delete(love);
    }

    @Override
    public int getCountOfLovesForVisual(Visual visual) {
        List<Love> lovesForVisual =
                loveRepository.findAllByVisual(visual);
        int loveCount = lovesForVisual.size();
        return loveCount;
    }

    @Override
    public Boolean chefLovedVisual(Visual visual, Chef chef) {
        Love love = loveRepository.findLoveByVisualAndChef(visual, chef);

        if (love == null) {
            return false;
        } else return true;
    }
}
