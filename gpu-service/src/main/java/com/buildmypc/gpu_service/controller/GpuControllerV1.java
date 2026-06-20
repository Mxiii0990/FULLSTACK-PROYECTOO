package com.buildmypc.gpu_service.controller;

import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.service.GpuService;
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
@RequestMapping("/api/v1/gpus")
@Tag(name="GPU V1", description = "CRUD estándar para especificaciones de Tarjetas Gráficas.")
public class GpuControllerV1 {

    @Autowired
    private GpuService service;

    @PostMapping
    @Operation(summary = "Crear especificación de GPU", description = "Valida la existencia del componente base y guarda los detalles técnicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "GPU creada con éxito")
    })
    public ResponseEntity<GpuResponseDTO> crearGpu(@Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO nueva = service.crearGpu(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las GPUs")
    public ResponseEntity<List<GpuResponseDTO>> listarGpus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar GPU por ID")
    public ResponseEntity<GpuResponseDTO> obtenerGpuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especificaciones de GPU")
    public ResponseEntity<GpuResponseDTO> actualizarGpu(@PathVariable Long id, @Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO actualizada = service.actualizarGpu(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar GPU")
    public ResponseEntity<Void> eliminarGpu(@PathVariable Long id) {
        service.eliminarGpu(id);
        return ResponseEntity.noContent().build();
    }
}