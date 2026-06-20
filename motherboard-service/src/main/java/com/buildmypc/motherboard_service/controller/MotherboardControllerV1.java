package com.buildmypc.motherboard_service.controller;

import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.service.MotherboardService;
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
@RequestMapping("/api/v1/motherboards")
@Tag(name="Motherboard V1", description = "CRUD estándar para especificaciones de Placas Madres.")
public class MotherboardControllerV1 {

    @Autowired
    private MotherboardService service;

    @PostMapping
    @Operation(summary = "Crear especificación de Placa Madre", description = "Valida la existencia del componente base y guarda los detalles técnicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Motherboard creada con éxito")
    })
    public ResponseEntity<MotherboardResponseDTO> crearMotherboard(@Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO nueva = service.crearMotherboard(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las Placas Madres")
    public ResponseEntity<List<MotherboardResponseDTO>> listarMotherboards() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Placa Madre por ID")
    public ResponseEntity<MotherboardResponseDTO> obtenerMotherboardPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de Placa Madre")
    public ResponseEntity<MotherboardResponseDTO> actualizarMotherboard(@PathVariable Long id, @Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO actualizada = service.actualizarMotherboard(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Placa Madre")
    public ResponseEntity<Void> eliminarMotherboard(@PathVariable Long id) {
        service.eliminarMotherboard(id);
        return ResponseEntity.noContent().build();
    }
}