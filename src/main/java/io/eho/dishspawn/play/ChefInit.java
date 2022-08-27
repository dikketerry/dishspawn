package io.eho.dishspawn.play;

import io.eho.dishspawn.controller.ChefController;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.repository.ChefRepository;
import io.eho.dishspawn.service.implementation.ChefServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ChefInit {

    // to fill DB with testing data
    // first run:
    // spring.jpa.hibernate.ddl-auto=create
    // assuming model is OK:
    // spring.jpa.hibernate.ddl-auto=update


    public static void main(String[] args) {

        // create chefService
        ChefServiceImpl chefService = new ChefServiceImpl();

        // create new Chef
        Chef newChef = new Chef();
        newChef.setUserName("masterchef");
        newChef.setEmail("master@cooking.com");
        newChef.setPassword("test123");
        newChef.setAvatarPath("/img/default-avatar.png");

        // save new Chef to DB
        chefService.saveChef(newChef);

    }

}
