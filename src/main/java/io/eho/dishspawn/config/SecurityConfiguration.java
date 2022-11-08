package io.eho.dishspawn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers("/chef/**", "/home", "recipe**", "/search**", "/spawn**", "/visual**").permitAll()
                .mvcMatchers("/recipe/add", "/loveVisual").hasAnyRole("chef", "superchef", "admin")
                .mvcMatchers("/ingredient/add").hasAnyRole("superchef", "admin")
                .mvcMatchers("/api/**", "/ingredient/all", "/chef/all", "/recipe/all").hasRole("admin")
//                .and().formLogin().loginPage("/sign-in").permitAll()
//                .and().logout().logoutUrl("/sign-out").permitAll()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
