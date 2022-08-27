package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChefService {

    void saveChef(Chef chef);
    List<Chef> getAllChefs();
    Chef findChefById(Long id);
    List<Chef> getAllChefsByUserNameContaining(String input);
    void updateChef(Chef chef);
    void deleteChef(Chef chef);

}
