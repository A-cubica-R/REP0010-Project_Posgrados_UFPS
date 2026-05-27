package ufps.edu.co.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import ufps.edu.co.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private static final String[] SUPER_ADMIN_PATHS = {
                        "/**"
        };

        private static final String[] PUBLIC_PATHS = {
                        "/auth/login",
                        "/auth/refresh",
                        "/api/application/case/login/recoveryPassword",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/v3/api-docs/**",
                        "/error",
                        "/api/application/case/inscripciones/usuario",
                        "/api/application/case/inscripciones/zonas-residencia",
                        "/api/application/case/inscripciones/tipos-vinculacion",
                        "/api/application/case/inscripciones/tipos-documento",
                        "/api/application/case/inscripciones/requisitos",
                        "/api/application/case/inscripciones/pueblos-indigenas",
                        "/api/application/case/inscripciones/programa/{programaId}/cohortes",
                        "/api/application/case/inscripciones/paises",
                        "/api/application/case/inscripciones/paises/{idPais}/departamentos",
                        "/api/application/case/inscripciones/grupos-etnicos",
                        "/api/application/case/inscripciones/generos",
                        "/api/application/case/inscripciones/estados-civiles",
                        "/api/application/case/inscripciones/discapacidades",
                        "/api/application/case/inscripciones/departamentos/{idDepartamento}/municipios",
                        "/api/application/case/inscripciones/capacidades-excepcionales"
        };

        private static final String[] DIRECTOR_PROGRAMA_PATHS = {
                        "/api/application/case/director-programa/**",
                        "/api/application/case/cohortes/**",
                        "/api/application/case/documentos/**",
                        "/api/dev/endpoint/tipoentrevista/listall",
                        "/api/dev/endpoint/estado/listall",
                        "/api/application/case/cohortes/**",
                        "/api/application/case/documentos/**",
                        "/api/dev/endpoint/tipoentrevista/listall",
                        "/api/dev/endpoint/estado/listall",
                        "/api/application/case/director-posgrados/**"
        };

        private static final String[] POSGRADOS_PATHS = {
                        "/api/dev/endpoint/otrosvalores/listall",
                        "/api/dev/endpoint/sedes/listall",
                        "/api/dev/endpoint/administrativo/listall",
                        "/api/dev/endpoint/facultad/listall",
                        "/api/dev/endpoint/programa/listbyfacultad",
                        "/api/dev/endpoint/programa/listall",
                        "/api/dev/endpoint/estado/listall",
                        "/api/dev/endpoint/semestre/listall"
        };

        private static final String[] ASPIRANTE_PATHS = {
                        "/api/application/case/aspirantes/**",
                        "/api/application/case/inscripciones/**"
        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource,
                        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth

                                                // Permite todas las solicitudes OPTIONS para CORS preflight
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // Rutas públicas sin autenticación
                                                .requestMatchers(PUBLIC_PATHS)
                                                .permitAll()

                                                // Rutas específicas por rol (antes del catch-all)
                                                .requestMatchers(POSGRADOS_PATHS)
                                                .hasAnyRole("POSGRADOS", "SUPER_ADMINISTRADOR")

                                                // Rutas específicas por rol (antes del catch-all)
                                                .requestMatchers(DIRECTOR_PROGRAMA_PATHS)
                                                .hasAnyRole("DIRECTOR_DE_PROGRAMA", "SUPER_ADMINISTRADOR")

                                                // Rutas específicas por rol (antes del catch-all)
                                                .requestMatchers(ASPIRANTE_PATHS)
                                                .hasAnyRole("ASPIRANTE", "DIRECTOR_DE_PROGRAMA", "SUPER_ADMINISTRADOR")

                                                // Rutas protegidas para el rol SUPER_ADMINISTRADOR (catch-all)
                                                .requestMatchers(SUPER_ADMIN_PATHS)
                                                .hasRole("SUPER_ADMINISTRADOR")

                                                // Denegar cualquier otra solicitud no especificada
                                                .anyRequest().denyAll())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
