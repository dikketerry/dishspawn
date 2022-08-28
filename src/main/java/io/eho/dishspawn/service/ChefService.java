package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChefService {

    void saveChef(Chef chef);
    List<Chef> findAllChefs();
    Chef findChefById(Long id);
    List<Chef> findAllChefByUserNameContaining(String input);
    void updateChef(Chef chef);
    void deleteChef(Chef chef);

}
