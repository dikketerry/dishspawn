package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.exception.ResourceNotFoundException;
import io.eho.dishspawn.exception.UsernameAlreadyExistsException;
import io.eho.dishspawn.model.Chef;
import io.eho.dishspawn.model.Role;
import io.eho.dishspawn.repository.ChefRepository;
import io.eho.dishspawn.repository.RoleRepository;
import io.eho.dishspawn.security.SecurityChef;
import io.eho.dishspawn.service.ChefService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChefServiceImpl implements ChefService, UserDetailsService {

    private final ChefRepository chefRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Chef> optionalChef = chefRepository.findChefByUserName(userName);
        return optionalChef.map(c -> new SecurityChef(c))
                .orElseThrow(() -> new UsernameNotFoundException("userName: " + userName + " not found."));
    }

    // todo refactor to return Chef
    @Override
    public void saveChef(Chef chef) {
        String userName = chef.getUserName();
        if (chefRepository.findChefByUserName(userName).isEmpty()) {
            chef.setRoles(defaultRole());
            chefRepository.save(chef);
        } else throw new UsernameAlreadyExistsException("username " + userName + " already exists.");
    }

    @Override
    public List<Chef> findAllChefs() {
        return chefRepository.findAll();
    }

    @Override
    public Chef findChefById(Long id) {
        Optional<Chef> optionalChef = chefRepository.findById(id);
        return optionalChef.orElseThrow(() -> new ResourceNotFoundException("chef with id " + id + " not found."));
    }

    @Override
    public List<Chef> findAllChefByUserNameContaining(String searchKey) {
        return chefRepository.findAllByUserNameContainingIgnoreCaseOrderByUserNameAsc(searchKey);
    }

    @Override
    public void updateChef(Chef chef) {
        chefRepository.save(chef);
    }

    @Override
    public void deleteChef(Chef chef) {
        chefRepository.delete(chef);
    }

    @Override
    public Chef findChefByUserName(String userName) {
        Optional<Chef> optionalChef = chefRepository.findChefByUserName(userName);
        return optionalChef.orElseThrow(() -> new UsernameNotFoundException("username " + userName + " not found"));
    }

    private Set<Role> defaultRole() {
        Optional<Role> role = roleRepository.findById(276l);
        Set<Role> defaultRole = new HashSet<>();
        defaultRole.add(role.get());
        return defaultRole;
    }
}
