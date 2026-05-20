package com.buildmypc.storage_service.controller;
import com.buildmypc.storage_service.dto.StorageRequestDTO;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import com.buildmypc.storage_service.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storages")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping
    public ResponseEntity<StorageResponseDTO> crearStorage(@Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO nuevo = service.crearStorage(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StorageResponseDTO>> listarStorages() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageResponseDTO> obtenerStoragePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageResponseDTO> actualizarStorage(@PathVariable Long id, @Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO actualizado = service.actualizarStorage(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarStorage(@PathVariable Long id) {
        service.eliminarStorage(id);
        return ResponseEntity.noContent().build();
    }
}
