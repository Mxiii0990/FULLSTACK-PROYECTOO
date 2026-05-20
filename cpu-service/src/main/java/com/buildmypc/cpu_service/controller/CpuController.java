package com.buildmypc.cpu_service.controller;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.service.CpuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cpus")
public class CpuController {

    @Autowired
    private CpuService service;

    @PostMapping
    public ResponseEntity<CpuResponseDTO> crearCpu(@Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO nuevo = service.crearCpu(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CpuResponseDTO>> listarCpus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CpuResponseDTO> obtenerCpuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CpuResponseDTO> actualizarCpu(@PathVariable Long id, @Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO actualizado = service.actualizarCpu(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCpu(@PathVariable Long id) {
        service.eliminarCpu(id);
        return ResponseEntity.noContent().build();
    }
}
