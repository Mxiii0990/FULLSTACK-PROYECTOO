package com.buildmypc.builder_service.controller;

import com.buildmypc.builder_service.dto.PcBuildRequestDTO;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import com.buildmypc.builder_service.service.PcBuildService;
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
@RequestMapping("/api/builds")
@Tag(name="Builder V1", description = "Metodos CRUD para el Builder. Gestiona la validación y el armado final de los equipos.")
public class PcBuildControllerV2 {

    @Autowired
    private PcBuildService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo ensamble de PC", description = "Recibe los IDs de los componentes, valida su compatibilidad y guarda el ensamble final en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PC ensamblado y creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos enviados (Ej: IDs nulos)")
    })
    public ResponseEntity<PcBuildResponseDTO> crearEnsamble(@Valid @RequestBody PcBuildRequestDTO request) {
        PcBuildResponseDTO nuevo = service.crearEnsamble(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los ensambles", description = "Retorna el historial completo de todos los computadores armados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ensambles obtenida exitosamente")
    })
    public ResponseEntity<List<PcBuildResponseDTO>> listarEnsambles() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ensamble por ID", description = "Busca los detalles de un computador armado específico utilizando su ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ensamble encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe un ensamble con el ID proporcionado")
    })
    public ResponseEntity<PcBuildResponseDTO> obtenerEnsamblePorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ensamble", description = "Modifica las piezas de un PC armado previamente. Requiere el ID del ensamble y los nuevos datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ensamble actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en el JSON"),
            @ApiResponse(responseCode = "404", description = "El ensamble que se intenta actualizar no existe")
    })
    public ResponseEntity<PcBuildResponseDTO> actualizarEnsamble(@PathVariable Long id, @Valid @RequestBody PcBuildRequestDTO request) {
        PcBuildResponseDTO actualizado = service.actualizarEnsamble(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ensamble", description = "Elimina de forma permanente un registro de PC armado del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ensamble eliminado exitosamente (Sin contenido de respuesta)"),
            @ApiResponse(responseCode = "404", description = "El ensamble que se intenta eliminar no existe")
    })
    public ResponseEntity<Void> eliminarEnsamble(@PathVariable Long id) {
        service.eliminarEnsamble(id);
        return ResponseEntity.noContent().build();
    }
}