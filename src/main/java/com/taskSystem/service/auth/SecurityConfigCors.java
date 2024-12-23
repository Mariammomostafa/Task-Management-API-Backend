package com.taskSystem.service.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// this class solve CORS problem which appear in angular when get employee from backend
@Configuration
public class SecurityConfigCors {
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**"); // Enable CORS for all application 
               
            }
        };
    }

}


/*
 * 
 * @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE") // Enable CORS for all application 
                .allowedOrigins("*")
                .allowedHeaders("*");
            }
        };
    }

 * */
