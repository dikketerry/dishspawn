package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.repository.ChefRepository;
import io.eho.dishspawn.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChefServiceImpl implements ChefService {

    private ChefRepository chefRepository;

    public ChefServiceImpl() {}

    @Autowired
    public ChefServiceImpl(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    @Override
    public void saveChef(Chef chef) {
        chefRepository.save(chef);
    }

    @Override
    public List<Chef> findAllChefs() {
        return chefRepository.findAll();
    }

    @Override
    public Chef findChefById(Long id) {
        Optional<Chef> optionalChef = chefRepository.findById(id);

        Chef chef = null;
        if(optionalChef.isPresent()) {
            chef = optionalChef.get();
        } else {
            // chef not found
            throw new RuntimeException("chef with id " + id + "not found");
        }

        return chef;
    }

    @Override
    public List<Chef> findAllChefByUserNameContaining(String input) {
        return chefRepository.findAllByUserNameContaining(input);
    }

    @Override
    public void updateChef(Chef chef) {
        chefRepository.save(chef);
    }

    @Override
    public void deleteChef(Chef chef) {
        chefRepository.delete(chef);
    }
}
