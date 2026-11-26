package com.buildmypc.builder_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Remueve por completo la cabecera de autorización para las llamadas internas
            template.removeHeader("Authorization");
        };
    }
}