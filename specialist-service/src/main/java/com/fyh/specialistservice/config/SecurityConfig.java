package com.fyh.specialistservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CorsConfigurationSource corsSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsSource))

                .authorizeHttpRequests(auth -> auth
                        // ————————————————
                        // 1) endpoint-urile pe care VREI să le securizezi:
                        // orice modificare sau acces detaliat la un specialist
//                        .requestMatchers(HttpMethod.POST,   "/api/specialisti/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT,    "/api/specialisti/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/api/specialisti/**").authenticated()
//                        .requestMatchers(HttpMethod.GET,    "/api/specialisti/*"  ).authenticated()

                        // 2) restul este PERMIȚII PUBLIC:
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Primary
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:4200"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
