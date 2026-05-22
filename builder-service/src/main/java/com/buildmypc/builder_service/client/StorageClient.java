package com.buildmypc.builder_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "storage-service", url = "http://localhost:8086/api/storages") // Asegúrate de que esta URL coincida con tu controller
public interface StorageClient {
    @GetMapping("/{id}")
    Object obtenerStoragePorId(@PathVariable("id") Long id);
}