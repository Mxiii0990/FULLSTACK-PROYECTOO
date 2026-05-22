package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gpu-service", url = "http://localhost:8085/api/gpus")
public interface GpuClient {
    @GetMapping("/{id}")
    Object obtenerGpuPorId(@PathVariable("id") Long id);
}