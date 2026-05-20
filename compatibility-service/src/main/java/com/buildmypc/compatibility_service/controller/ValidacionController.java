package com.buildmypc.compatibility_service.controller;

import com.buildmypc.compatibility_service.dto.ValidacionRequestDTO;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import com.buildmypc.compatibility_service.service.ValidacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validations")
public class ValidacionController {

    @Autowired
    private ValidacionService service;

    @PostMapping
    public ResponseEntity<ValidacionResponseDTO> crearValidacion(@Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO nueva = service.crearValidacion(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ValidacionResponseDTO>> listarValidaciones() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValidacionResponseDTO> obtenerValidacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValidacionResponseDTO> actualizarValidacion(@PathVariable Long id, @Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO actualizada = service.actualizarValidacion(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarValidacion(@PathVariable Long id) {
        service.eliminarValidacion(id);
        return ResponseEntity.noContent().build();
    }
}