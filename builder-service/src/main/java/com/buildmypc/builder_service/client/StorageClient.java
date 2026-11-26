package com.buildmypc.builder_service.client;

import com.buildmypc.builder_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "storage-service", configuration = FeignConfig.class)
public interface StorageClient {

    @GetMapping("/api/v1/storages/{id}")
    Object obtenerStoragePorId(@PathVariable("id") Long id);
}