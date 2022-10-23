package io.eho.dishspawn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsManager uds = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("erik")
                .password("test123")
                .authorities("read")
                .build();

        UserDetails user2 = User.withUsername("ramo")
                .password("test321")
                .authorities("read")
                .build();

        uds.createUser(user1);
        uds.createUser(user2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
