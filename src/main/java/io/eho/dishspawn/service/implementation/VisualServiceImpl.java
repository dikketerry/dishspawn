package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Visual;
import io.eho.dishspawn.repository.VisualRepository;
import io.eho.dishspawn.service.VisualService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisualServiceImpl implements VisualService {

    private VisualRepository visualRepository;

    public VisualServiceImpl(VisualRepository visualRepository) {
        this.visualRepository = visualRepository;
    }

    @Override
    public void saveVisual(Visual visual, Chef chef) {
        visual.setChef(chef);
        visualRepository.save(visual);
    }

    @Override
    public void deleteVisual(Visual visual, Chef chef) {

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

        Visual visual = null;
        if(optionalVisual.isPresent()) {
            visual = optionalVisual.get();
        } else {
            throw new RuntimeException("visual with id " + id + " not found");
        }
        return visual;
    }
}
