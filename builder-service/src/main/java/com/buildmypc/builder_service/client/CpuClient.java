package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpu-service", url = "http://localhost:8082/api/cpus")
public interface CpuClient {
    @GetMapping("/{id}")
    Object obtenerCpuPorId(@PathVariable("id") Long id);
}