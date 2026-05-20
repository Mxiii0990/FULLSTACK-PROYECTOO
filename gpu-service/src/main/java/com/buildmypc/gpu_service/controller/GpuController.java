package com.buildmypc.gpu_service.controller;
import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.service.GpuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpus")
public class GpuController {

    @Autowired
    private GpuService service;

    @PostMapping
    public ResponseEntity<GpuResponseDTO> crearGpu(@Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO nueva = service.crearGpu(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GpuResponseDTO>> listarGpus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GpuResponseDTO> obtenerGpuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GpuResponseDTO> actualizarGpu(@PathVariable Long id, @Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO actualizada = service.actualizarGpu(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGpu(@PathVariable Long id) {
        service.eliminarGpu(id);
        return ResponseEntity.noContent().build();
    }
}
