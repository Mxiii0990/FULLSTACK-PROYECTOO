package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "psu-service", url = "http://localhost:8087/api/psus")
public interface PsuClient {
    @GetMapping("/{id}")
    Object obtenerPsuPorId(@PathVariable("id") Long id);
}