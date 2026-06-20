package com.buildmypc.user_service.controller;

import com.buildmypc.user_service.dto.UserRequestDTO;
import com.buildmypc.user_service.dto.UserResponseDTO;
import com.buildmypc.user_service.service.UserService;
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
@RequestMapping("/api/v1/users")
@Tag(name="Usuarios V1", description = "CRUD estándar para gestión de identidades y roles.")
public class UserControllerV1 {

    @Autowired
    private UserService service;

    @PostMapping
    @Operation(summary = "Crear Usuario", description = "Registra un nuevo usuario con su rol (CLIENTE o ADMIN).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito")
    })
    public ResponseEntity<UserResponseDTO> crearUsuario(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO nuevo = service.crearUsuario(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<List<UserResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuario por ID")
    public ResponseEntity<UserResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de Usuario")
    public ResponseEntity<UserResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO actualizado = service.actualizarUsuario(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Usuario")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}