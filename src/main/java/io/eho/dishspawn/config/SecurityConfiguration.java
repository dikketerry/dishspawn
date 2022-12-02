package io.eho.dishspawn.config;

import io.eho.dishspawn.security.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
                    .loginPage("/login")       // will redirect when login is required
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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
