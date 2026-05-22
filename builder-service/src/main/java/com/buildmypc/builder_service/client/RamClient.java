package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ram-service", url = "http://localhost:8083/api/rams")
public interface RamClient {
    @GetMapping("/{id}")
    Object obtenerRamPorId(@PathVariable("id") Long id);
}