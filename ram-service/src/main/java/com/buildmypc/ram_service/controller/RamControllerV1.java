package com.buildmypc.ram_service.controller;

import com.buildmypc.ram_service.dto.RamRequestDTO;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import com.buildmypc.ram_service.service.RamService;
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
@RequestMapping("/api/v1/rams")
@Tag(name="RAM V1", description = "CRUD estándar para especificaciones de Memoria RAM.")
public class RamControllerV1 {

    @Autowired
    private RamService service;

    @PostMapping
    @Operation(summary = "Crear especificación de Memoria RAM", description = "Valida la existencia del componente base y guarda capacidad, frecuencia y tipo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memoria RAM creada con éxito")
    })
    public ResponseEntity<RamResponseDTO> crearRam(@Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO nuevaRam = service.crearRam(request);
        return new ResponseEntity<>(nuevaRam, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las Memorias RAM")
    public ResponseEntity<List<RamResponseDTO>> listarRams() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Memoria RAM por ID")
    public ResponseEntity<RamResponseDTO> obtenerRamPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de RAM")
    public ResponseEntity<RamResponseDTO> actualizarRam(@PathVariable Long id, @Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO ramActualizada = service.actualizarRam(id, request);
        return ResponseEntity.ok(ramActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Memoria RAM")
    public ResponseEntity<Void> eliminarRam(@PathVariable Long id) {
        service.eliminarRam(id);
        return ResponseEntity.noContent().build();
    }
}