package com.fyh.utilizatorservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CorsConfigurationSource corsSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsSource))

                .authorizeHttpRequests(auth -> auth
                        // — endpoint-urile pe care VREI să le securizezi —
//                        .requestMatchers(HttpMethod.POST,   "/api/utilizatori/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT,    "/api/utilizatori/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/api/utilizatori/**").authenticated()
//                        // (dacă vrei acces restricționat și la GET pe detaliu “full”)
//                        .requestMatchers(HttpMethod.GET,    "/api/utilizatori/{id}").authenticated()

                        // — tot restul e PUBLIC —
                        // login/înregistrare
                        .requestMatchers("/api/auth/**").permitAll()
                        // lista completă (dacă vrei să o expui)
                        .requestMatchers(HttpMethod.GET, "/api/utilizatori").permitAll()
                        // datele “public” per user
                        .requestMatchers(HttpMethod.GET, "/api/utilizatori/*/public").permitAll()

                        // orice alt request neașteptat rămâne permis
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    @Primary
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
