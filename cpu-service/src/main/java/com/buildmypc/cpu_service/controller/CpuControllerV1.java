package com.buildmypc.cpu_service.controller;

import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.service.CpuService;
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
@RequestMapping("/api/v1/cpus")
@Tag(name="CPU V1", description = "CRUD estándar para especificaciones de procesadores.")
public class CpuControllerV1 {

    @Autowired
    private CpuService service;

    @PostMapping
    @Operation(summary = "Crear especificación de CPU", description = "Valida el componentId mediante OpenFeign y guarda los detalles técnicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CPU creada con éxito")
    })
    public ResponseEntity<CpuResponseDTO> crearCpu(@Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO nuevo = service.crearCpu(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las CPUs")
    public ResponseEntity<List<CpuResponseDTO>> listarCpus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar CPU por ID")
    public ResponseEntity<CpuResponseDTO> obtenerCpuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de CPU")
    public ResponseEntity<CpuResponseDTO> actualizarCpu(@PathVariable Long id, @Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO actualizado = service.actualizarCpu(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar CPU")
    public ResponseEntity<Void> eliminarCpu(@PathVariable Long id) {
        service.eliminarCpu(id);
        return ResponseEntity.noContent().build();
    }
}