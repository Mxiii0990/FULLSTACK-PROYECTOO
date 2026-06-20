package com.buildmypc.storage_service.controller;

import com.buildmypc.storage_service.dto.StorageRequestDTO;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import com.buildmypc.storage_service.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storages")
@Tag(name="Storage V1", description = "CRUD estándar para especificaciones de Almacenamiento.")
public class StorageControllerV1 {

    @Autowired
    private StorageService service;

    @PostMapping
    @Operation(summary = "Crear especificación de Almacenamiento", description = "Valida la existencia del componente base y guarda capacidad, velocidad y tipo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidad de Almacenamiento creada con éxito")
    })
    public ResponseEntity<StorageResponseDTO> crearStorage(@Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO nuevo = service.crearStorage(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las unidades de Almacenamiento")
    public ResponseEntity<List<StorageResponseDTO>> listarStorages() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Almacenamiento por ID")
    public ResponseEntity<StorageResponseDTO> obtenerStoragePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de Almacenamiento")
    public ResponseEntity<StorageResponseDTO> actualizarStorage(@PathVariable Long id, @Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO actualizado = service.actualizarStorage(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Unidad de Almacenamiento")
    public ResponseEntity<Void> eliminarStorage(@PathVariable Long id) {
        service.eliminarStorage(id);
        return ResponseEntity.noContent().build();
    }
}