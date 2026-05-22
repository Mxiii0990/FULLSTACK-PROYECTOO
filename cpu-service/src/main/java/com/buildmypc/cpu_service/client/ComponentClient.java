package com.buildmypc.cpu_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Asumimos que component-service corre en el puerto 8081
@FeignClient(name = "component-service", url = "http://localhost:8081/api/components")
public interface ComponentClient {

    @GetMapping("/{id}")
    Object obtenerComponentePorId(@PathVariable("id") Long id);
}