package com.buildmypc.compatibility_service.controller;

import com.buildmypc.compatibility_service.dto.ValidacionRequestDTO;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import com.buildmypc.compatibility_service.service.ValidacionService;
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
@RequestMapping("/api/v1/validations")
@Tag(name="Validacion V1", description = "Metodos CRUD para las validaciones. Gestiona la verificación de compatibilidad y consumo de los equipos.")
public class ValidacionControllerV1 {

    @Autowired
    private ValidacionService service;

    @PostMapping
    @Operation(summary = "Crear una nueva validación", description = "Recibe los datos de compatibilidad y consumo, valida su existencia con el ensamble y guarda el registro en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Validación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados (Ej: IDs nulos)")
    })
    public ResponseEntity<ValidacionResponseDTO> crearValidacion(@Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO nueva = service.crearValidacion(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las validaciones", description = "Retorna el historial completo de todas las validaciones de compatibilidad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de validaciones obtenida exitosamente")
    })
    public ResponseEntity<List<ValidacionResponseDTO>> listarValidaciones() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar validación por ID", description = "Busca los detalles de una validación específica utilizando su ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validación encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe una validación con el ID proporcionado")
    })
    public ResponseEntity<ValidacionResponseDTO> obtenerValidacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una validación", description = "Modifica los datos de una validación existente. Requiere el ID y los nuevos datos de consumo o compatibilidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en el JSON"),
            @ApiResponse(responseCode = "404", description = "La validación que se intenta actualizar no existe")
    })
    public ResponseEntity<ValidacionResponseDTO> actualizarValidacion(@PathVariable Long id, @Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO actualizada = service.actualizarValidacion(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una validación", description = "Elimina de forma permanente un registro de validación del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Validación eliminada exitosamente (Sin contenido de respuesta)"),
            @ApiResponse(responseCode = "404", description = "La validación que se intenta eliminar no existe")
    })
    public ResponseEntity<Void> eliminarValidacion(@PathVariable Long id) {
        service.eliminarValidacion(id);
        return ResponseEntity.noContent().build();
    }
}