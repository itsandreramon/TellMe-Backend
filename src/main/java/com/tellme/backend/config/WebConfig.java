/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET, POST, PUT, DELETE, OPTIONS")
                .allowedHeaders("*");
    }
}