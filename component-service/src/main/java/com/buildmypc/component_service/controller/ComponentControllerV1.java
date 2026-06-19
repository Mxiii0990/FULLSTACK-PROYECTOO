package com.buildmypc.component_service.controller;

import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.service.ComponentService;
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
@RequestMapping("/api/v1/components")
@Tag(name="Componentes V1", description = "CRUD estándar para el catálogo de componentes.")
public class ComponentControllerV1 {

    @Autowired
    private ComponentService service;

    @PostMapping
    @Operation(summary = "Crear componente", description = "Agrega un nuevo componente al catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Componente creado con éxito")
    })
    public ResponseEntity<ComponentResponseDTO> crearComponente(@Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO nuevo = service.crearComponente(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los componentes", description = "Retorna el catálogo completo.")
    public ResponseEntity<List<ComponentResponseDTO>> listarComponentes() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar componente por ID")
    public ResponseEntity<ComponentResponseDTO> obtenerComponentePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar componente")
    public ResponseEntity<ComponentResponseDTO> actualizarComponente(@PathVariable Long id, @Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO actualizado = service.actualizarComponente(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar componente")
    public ResponseEntity<Void> eliminarComponente(@PathVariable Long id) {
        service.eliminarComponente(id);
        return ResponseEntity.noContent().build();
    }
}