package com.buildmypc.psu_service.controller;

import com.buildmypc.psu_service.assemblers.PsuModelAssembler;
import com.buildmypc.psu_service.dto.PsuRequestDTO;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import com.buildmypc.psu_service.service.PsuService;
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
@RequestMapping("/api/v2/psus")
@Validated
@Tag(name="PSU V2", description = "API HATEOAS para la gestión técnica de Fuentes de Poder.")
public class PsuControllerV2 {

    @Autowired
    private PsuService service;

    @Autowired
    private PsuModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Fuente de Poder")
    public ResponseEntity<EntityModel<PsuResponseDTO>> crearPsu(@Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO nueva = service.crearPsu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de Fuentes de Poder")
    public ResponseEntity<CollectionModel<EntityModel<PsuResponseDTO>>> listarPsus() {
        List<EntityModel<PsuResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(PsuControllerV2.class).listarPsus()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar PSU por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PsuResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 33, \"watts\": 850, \"certificacion\": \"80 Plus Gold\", \"modular\": true}")))
    })
    public ResponseEntity<EntityModel<PsuResponseDTO>> obtenerPsuPorId(
            @Parameter(description = "Id de la Fuente de Poder", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar PSU")
    public ResponseEntity<EntityModel<PsuResponseDTO>> actualizarPsu(@PathVariable Long id, @Valid @RequestBody PsuRequestDTO request) {
        PsuResponseDTO actualizada = service.actualizarPsu(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar PSU")
    public ResponseEntity<Void> eliminarPsu(@PathVariable Long id) {
        service.eliminarPsu(id);
        return ResponseEntity.noContent().build();
    }
}