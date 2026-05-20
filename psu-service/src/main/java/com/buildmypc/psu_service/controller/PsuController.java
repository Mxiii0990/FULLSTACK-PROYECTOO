package com.buildmypc.psu_service.controller;
import com.buildmypc.psu_service.dto.PsuRequestDTO;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import com.buildmypc.psu_service.service.PsuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psus")
public class PsuController {

    @Autowired
    private PsuService service;

    @PostMapping
    public ResponseEntity<PsuResponseDTO> crearPsu(@Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO nueva = service.crearPsu(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PsuResponseDTO>> listarPsus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsuResponseDTO> obtenerPsuPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PsuResponseDTO> actualizarPsu(@PathVariable Long id, @Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO actualizada = service.actualizarPsu(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPsu(@PathVariable Long id) {
        service.eliminarPsu(id);
        return ResponseEntity.noContent().build();
    }
}
