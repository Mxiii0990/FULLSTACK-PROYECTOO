package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpu-service", configuration = FeignConfig.class)
public interface CpuClient {

    @GetMapping("/api/v1/cpus/{id}")
    Object obtenerCpuPorId(@PathVariable("id") Long id);
}