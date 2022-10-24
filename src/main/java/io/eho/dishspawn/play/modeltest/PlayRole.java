package io.eho.dishspawn.play.modeltest;

import io.eho.dishspawn.model.Role;
import io.eho.dishspawn.repository.RoleRepository;
import io.eho.dishspawn.service.RoleService;
import io.eho.dishspawn.service.implementation.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayRole {

    public static void main(String[] args) {

        Role newRole = new Role();
        newRole.setName("chef");
    }

}
