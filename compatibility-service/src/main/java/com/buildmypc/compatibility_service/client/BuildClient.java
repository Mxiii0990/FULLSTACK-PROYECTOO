package com.buildmypc.compatibility_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "builder-service", url = "http://localhost:8088/api/builds")
public interface BuildClient {
    @GetMapping("/{id}")
    Object obtenerEnsamblePorId(@PathVariable("id") Long id);
}