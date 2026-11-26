package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gpu-service", configuration = FeignConfig.class)
public interface GpuClient {

    @GetMapping("/api/v1/gpus/{id}")
    Object obtenerGpuPorId(@PathVariable("id") Long id);
}