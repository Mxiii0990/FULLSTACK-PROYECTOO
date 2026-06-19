package com.buildmypc.compatibility_service.controller;

import com.buildmypc.compatibility_service.assemblers.CompatibilityModelAssembler;
import com.buildmypc.compatibility_service.dto.ValidacionRequestDTO;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import com.buildmypc.compatibility_service.service.ValidacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/validations")
@Validated
@Tag(name="Validacion V2", description = "API HATEOAS para validaciones de compatibilidad.")
public class ValidacionControllerV2 {

    @Autowired
    private ValidacionService service;

    // ¡AQUÍ ESTÁ EL CAMBIO! Ahora inyecta tu CompatibilityModelAssembler
    @Autowired
    private CompatibilityModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar validación")
    public ResponseEntity<EntityModel<ValidacionResponseDTO>> crearValidacion(@Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO nueva = service.crearValidacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de validaciones")
    public ResponseEntity<CollectionModel<EntityModel<ValidacionResponseDTO>>> listarValidaciones() {
        List<EntityModel<ValidacionResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(ValidacionControllerV2.class).listarValidaciones()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar validación por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidacionResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"buildId\": 15, \"compatible\": true, \"consumoEstimadoWatts\": 450.5, \"margenFuente\": 100.0, \"observaciones\": \"Consumo optimo\"}")))
    })
    public ResponseEntity<EntityModel<ValidacionResponseDTO>> obtenerValidacionPorId(
            @Parameter(description = "Id de la validacion", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar validación")
    public ResponseEntity<EntityModel<ValidacionResponseDTO>> actualizarValidacion(@PathVariable Long id, @Valid @RequestBody ValidacionRequestDTO request) {
        ValidacionResponseDTO actualizada = service.actualizarValidacion(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar validación")
    public ResponseEntity<Void> eliminarValidacion(@PathVariable Long id) {
        service.eliminarValidacion(id);
        return ResponseEntity.noContent().build();
    }
}