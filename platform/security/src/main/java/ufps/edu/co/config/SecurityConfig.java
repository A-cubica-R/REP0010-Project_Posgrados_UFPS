package ufps.edu.co.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/posgrados-project/swagger-ui/**",
                                "/posgrados-project/v3/api-docs/**",
                                "/posgrados-project/swagger-ui.html",
                                "/posgrados-project/swagger-ui/index.html",
                                "/posgrados-project/**")
                        .permitAll()
                        .anyRequest()
                        .permitAll()); // Permitir acceso público temporalmente mientras se configura seguridad

        return http.build();
    }
}