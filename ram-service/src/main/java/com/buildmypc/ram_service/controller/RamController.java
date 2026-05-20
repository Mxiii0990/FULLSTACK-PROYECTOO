package com.buildmypc.ram_service.controller;
import com.buildmypc.ram_service.dto.RamRequestDTO;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import com.buildmypc.ram_service.service.RamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rams")
public class RamController {
    @Autowired
    private RamService service;

    @PostMapping
    public ResponseEntity<RamResponseDTO> crearRam(@Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO nuevaRam = service.crearRam(request);
        return new ResponseEntity<>(nuevaRam, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RamResponseDTO>> listarRams() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RamResponseDTO> obtenerRamPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RamResponseDTO> actualizarRam(@PathVariable Long id, @Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO ramActualizada = service.actualizarRam(id, request);
        return ResponseEntity.ok(ramActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRam(@PathVariable Long id) {
        service.eliminarRam(id);
        return ResponseEntity.noContent().build();
    }
}
