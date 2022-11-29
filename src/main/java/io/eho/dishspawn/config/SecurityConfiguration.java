package io.eho.dishspawn.config;

import io.eho.dishspawn.exception.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                .authorizeRequests()
                    .mvcMatchers("/api/**", "/ingredient/all", "/chef/all", "/recipe/all").hasRole("admin")
                    .mvcMatchers("/ingredient/add").hasAnyRole("superchef", "admin")
                    .mvcMatchers("/recipe/add", "/loveVisual").hasAnyRole("chef", "superchef", "admin")
                    .mvcMatchers("/chef/**", "/home", "recipe**", "/search**", "/spawn/**", "/visual/**", "/login",
                                 "/error").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login.html")       // form for login template
                    .loginProcessingUrl("/login")   // for the POST method
                    .defaultSuccessUrl("/home")
                    .failureHandler(authenticationFailureHandler())
                    .failureUrl("/error")
                .and()
                .logout()
                    .logoutSuccessUrl("/home")
                .deleteCookies("JSESSIONID");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
