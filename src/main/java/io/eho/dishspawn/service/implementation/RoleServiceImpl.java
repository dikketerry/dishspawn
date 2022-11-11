package io.eho.dishspawn.service.implementation;

import io.eho.dishspawn.model.Role;
import io.eho.dishspawn.repository.RoleRepository;
import io.eho.dishspawn.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
