package com.buildmypc.psu_service.controller;

import com.buildmypc.psu_service.dto.PsuRequestDTO;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import com.buildmypc.psu_service.service.PsuService;
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
@RequestMapping("/api/v1/psus")
@Tag(name="PSU V1", description = "CRUD estándar para especificaciones de Fuentes de Poder.")
public class PsuControllerV1 {

    @Autowired
    private PsuService service;

    @PostMapping
    @Operation(summary = "Crear especificación de Fuente de Poder", description = "Valida la existencia del componente base y guarda la capacidad en watts y certificación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fuente de Poder creada con éxito")
    })
    public ResponseEntity<PsuResponseDTO> crearPsu(@Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO nueva = service.crearPsu(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las Fuentes de Poder")
    public ResponseEntity<List<PsuResponseDTO>> listarPsus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Fuente de Poder por ID")
    public ResponseEntity<PsuResponseDTO> obtenerPsuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de PSU")
    public ResponseEntity<PsuResponseDTO> actualizarPsu(@PathVariable Long id, @Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO actualizada = service.actualizarPsu(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Fuente de Poder")
    public ResponseEntity<Void> eliminarPsu(@PathVariable Long id) {
        service.eliminarPsu(id);
        return ResponseEntity.noContent().build();
    }
}