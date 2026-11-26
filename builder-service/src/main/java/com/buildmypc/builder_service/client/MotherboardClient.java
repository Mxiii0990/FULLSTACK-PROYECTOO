package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "motherboard-service", configuration = FeignConfig.class)
public interface MotherboardClient {

    @GetMapping("/api/v1/motherboards/{id}")
    Object obtenerMotherboardPorId(@PathVariable("id") Long id);
}