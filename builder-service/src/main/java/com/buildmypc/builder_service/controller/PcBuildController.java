package com.buildmypc.builder_service.controller;
import com.buildmypc.builder_service.dto.PcBuildRequestDTO;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import com.buildmypc.builder_service.service.PcBuildService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/builds")
public class PcBuildController {

    @Autowired
    private PcBuildService service;

    @PostMapping
    public ResponseEntity<PcBuildResponseDTO> crearEnsamble(@Valid @RequestBody PcBuildRequestDTO request) {
        PcBuildResponseDTO nuevo = service.crearEnsamble(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PcBuildResponseDTO>> listarEnsambles() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PcBuildResponseDTO> obtenerEnsamblePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PcBuildResponseDTO> actualizarEnsamble(@PathVariable Long id, @Valid @RequestBody PcBuildRequestDTO request) {
        PcBuildResponseDTO actualizado = service.actualizarEnsamble(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnsamble(@PathVariable Long id) {
        service.eliminarEnsamble(id);
        return ResponseEntity.noContent().build();
    }
}
