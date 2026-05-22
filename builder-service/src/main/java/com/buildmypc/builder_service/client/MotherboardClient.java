package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "motherboard-service", url = "http://localhost:8084/api/motherboards")
public interface MotherboardClient {
    @GetMapping("/{id}")
    Object obtenerMotherboardPorId(@PathVariable("id") Long id);
}