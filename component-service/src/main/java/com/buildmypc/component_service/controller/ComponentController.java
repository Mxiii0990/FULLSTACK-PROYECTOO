package com.buildmypc.component_service.controller;
import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    @Autowired
    private ComponentService service;

    @PostMapping
    public ResponseEntity<ComponentResponseDTO> crearComponente(@Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO nuevo = service.crearComponente(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComponentResponseDTO>> listarComponentes() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> obtenerComponentePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> actualizarComponente(@PathVariable Long id, @Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO actualizado = service.actualizarComponente(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComponente(@PathVariable Long id) {
        service.eliminarComponente(id);
        return ResponseEntity.noContent().build();
    }
}
