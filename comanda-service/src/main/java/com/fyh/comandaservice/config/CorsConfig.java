package com.fyh.comandaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Aplica regula pentru toate endpoint-urile care incep cu /api
                        .allowedOrigins("http://localhost:4200") // Permite request-uri de la aplicatia Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodele HTTP permise
                        .allowedHeaders("*") // Permite toate headerele
                        .allowCredentials(true);
            }
        };
    }
}
