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
        http.csrf()
                .and().authorizeRequests()
                .mvcMatchers("/api/**", "/ingredient/all", "/chef/all", "/recipe/all").hasRole("admin")
                .mvcMatchers("/ingredient/add").hasAnyRole("superchef", "admin")
                .mvcMatchers("/recipe/add", "/loveVisual").hasAnyRole("chef", "superchef", "admin")
                .mvcMatchers("/chef/**", "/home", "recipe**", "/search**", "/spawn**", "/visual**").permitAll()
                .and().formLogin()
                .loginPage("/chef/login.html")
                .loginProcessingUrl("/chef/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .and().logout()
                .logoutSuccessUrl("/home")
                .deleteCookies("JSESSIONID");
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
