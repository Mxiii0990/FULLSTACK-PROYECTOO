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

    @GetMapping
    public ResponseEntity<List<ComponentResponseDTO>> listarComponent() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<ComponentResponseDTO> crearComponent(@Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO nuevoComponent = service.crearComponent(request);
        return new ResponseEntity<>(nuevoComponent, HttpStatus.CREATED);
    }
}
