package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.exception.ResourceNotFoundException;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.repository.VisualRepository;
import io.eho.dishspawn.service.VisualService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VisualServiceImpl implements VisualService {

    private final VisualRepository visualRepository;

    @Override
    public Long findNextIdValue() {
        return visualRepository.getNextValSequence();
    }

    @Override
    public void saveVisual(Visual visual) {
        visualRepository.save(visual);
    }

    @Override
    public void deleteVisual(Visual visual) {
        visualRepository.delete(visual);
    }

    @Override
    public List<Visual> findAllVisuals() {
        return visualRepository.findAllByOrderByTimestampCreatedDesc();
    }

    @Override
    public List<Visual> findLast200Visuals() {
        return visualRepository.findTop200ByOrderByTimestampCreatedDesc();
    }

    @Override
    public List<Visual> findAllVisualsForRecipe() {
        return null;
    }

    @Override
    public Visual findLatestVisual() {
        return visualRepository.findFirstByOrderByTimestampCreatedDesc();
    }

    @Override
    public Page<Visual> findPageVisuals(int pageNr) {

        int pageNumber = pageNr;
        int pageSize;

        if (pageNumber == 1) {
            pageSize = 4;
        } else pageSize = 3;

        Pageable pageable = PageRequest.of(pageNr - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "timestampCreated"));

        return visualRepository.findAll(pageable);
    }

    @Override
    public Visual findVisualById(Long id) {
        Optional<Visual> optionalVisual = visualRepository.findById(id);
        return optionalVisual.orElseThrow(() -> new ResourceNotFoundException("visual with id " + id + " not found."));
    }

    @Override
    public List<Visual> findAllVisualsForChef(Chef chef) {
        return visualRepository.findByChefOrderByTimestampCreatedDesc(chef);
    }

    @Override
    public List<Visual> findLast200VisualsForChef(Chef chef) {
        return visualRepository.findTop200ByChefOrderByTimestampCreatedDesc(chef);
    }

    @Override
    public Visual findLatestVisualForChef(Chef chef) {
        return visualRepository.findTop1ByChefOrderByTimestampCreatedDesc(chef);
    }

    @Override
    public Visual findLastVisualForRecipe(Recipe recipe) {
        return visualRepository.findTop1ByRecipeOrderByTimestampCreatedDesc(recipe);
    }
}
