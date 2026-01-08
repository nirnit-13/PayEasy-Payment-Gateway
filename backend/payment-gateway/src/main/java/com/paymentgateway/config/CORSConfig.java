package com.paymentgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Value("${frontend.url:http://localhost:5500}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("=== CORS Configuration ===");
        System.out.println("Configured Frontend URL: " + frontendUrl);
        System.out.println("========================");

        registry.addMapping("/**")
                .allowedOrigins(
                        // Local development
                        "http://localhost:5500",
                        "http://127.0.0.1:5500",
                        "http://localhost:3000",
                        // Production - Environment variable
                        frontendUrl,
                        // Netlify patterns
                        "https://*.netlify.app",
                        "https://payeasy-gateway.netlify.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}