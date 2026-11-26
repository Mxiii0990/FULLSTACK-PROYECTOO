package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "psu-service", configuration = FeignConfig.class)
public interface PsuClient {

    @GetMapping("/api/v1/psus/{id}")
    Object obtenerPsuPorId(@PathVariable("id") Long id);
}