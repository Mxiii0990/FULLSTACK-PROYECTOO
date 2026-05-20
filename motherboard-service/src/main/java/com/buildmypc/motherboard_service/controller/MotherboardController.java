package com.buildmypc.motherboard_service.controller;
import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.service.MotherboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motherboards")
public class MotherboardController {

    @Autowired
    private MotherboardService service;

    @PostMapping
    public ResponseEntity<MotherboardResponseDTO> crearMotherboard(@Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO nueva = service.crearMotherboard(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MotherboardResponseDTO>> listarMotherboards() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotherboardResponseDTO> obtenerMotherboardPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotherboardResponseDTO> actualizarMotherboard(@PathVariable Long id, @Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO actualizada = service.actualizarMotherboard(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMotherboard(@PathVariable Long id) {
        service.eliminarMotherboard(id);
        return ResponseEntity.noContent().build();
    }
}
