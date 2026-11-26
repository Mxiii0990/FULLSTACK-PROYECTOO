package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ram-service", configuration = FeignConfig.class)
public interface RamClient {

    @GetMapping("/api/v1/rams/{id}")
    Object obtenerRamPorId(@PathVariable("id") Long id);
}