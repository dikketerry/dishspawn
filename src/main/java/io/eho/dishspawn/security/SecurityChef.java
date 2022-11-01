package io.eho.dishspawn.security;

import io.eho.dishspawn.model.Chef;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecurityChef implements UserDetails {

    private final Chef chef;

    @Override
    public String getUsername() {
        return chef.getUserName();
    }

    @Override
    public String getPassword() {
        return chef.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(() -> "chef");
        return chef.getRoles()
                .stream()
                .map(role -> new SecurityRole(role))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
