package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Love;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.repository.LoveRepository;
import io.eho.dishspawn.repository.VisualRepository;
import io.eho.dishspawn.service.LoveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoveServiceImpl implements LoveService {

    private final LoveRepository loveRepository;
    private final VisualRepository visualRepository;

    @Override
    public void saveLove(Visual visual, Chef chef) {
        Love love = new Love();
        love.setVisual(visual);
        love.setChef(chef);
        visual.setLoveCount(visual.getLoveCount() + 1);
        loveRepository.save(love);
        visualRepository.save(visual);
    }

    @Override
    public void deleteLove(Visual visual, Chef chef) {
        Love love = loveRepository.findLoveByVisualAndChef(visual, chef);
        visual.setLoveCount(visual.getLoveCount() - 1);
        loveRepository.delete(love);
        visualRepository.save(visual);
    }

    // todo: not used - love count obtained via int loveCount in visual entity
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
