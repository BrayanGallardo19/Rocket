package com.example.GestionUsuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()  // Permite acceso a /auth/register, /auth/login, etc.
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Si utilizas Basic Auth en otros endpoints
        return http.build();
    }
}
