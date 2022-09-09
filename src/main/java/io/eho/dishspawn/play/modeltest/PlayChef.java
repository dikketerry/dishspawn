package io.eho.dishspawn.play;

import io.eho.dishspawn.controller.ChefController;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.repository.ChefRepository;
import io.eho.dishspawn.service.ChefService;
import io.eho.dishspawn.service.implementation.ChefServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayChef {

    public static void main(String[] args) {

        // create chefService
        ChefService chefService;

        // create new Chef
        Chef newChef = new Chef();
        newChef.setUserName("masterchef");
        newChef.setEmail("master@cooking.com");
        newChef.setPassword("test123");
        newChef.setAvatarPath("/img/default-avatar.png");

        System.out.println(newChef);

    }

}
