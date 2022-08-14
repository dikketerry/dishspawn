package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Chef;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ChefService {

    // create
    void saveChef(Chef chef);

    // read - get all chefs from DB
    List<Chef> getAllChefs();

    // read - get chef(s) based on string input
    List<Chef> getAllChefsByUserNameContaining(String input);

    // update
    void updateChef(Chef chef);

    // delete
    void deleteChef(Chef chef);

}
