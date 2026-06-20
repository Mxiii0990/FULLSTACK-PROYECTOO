package com.buildmypc.ram_service.controller;

import com.buildmypc.ram_service.assemblers.RamModelAssembler;
import com.buildmypc.ram_service.dto.RamRequestDTO;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import com.buildmypc.ram_service.service.RamService;
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
@RequestMapping("/api/v2/rams")
@Validated
@Tag(name="RAM V2", description = "API HATEOAS para la gestión técnica de Memorias RAM.")
public class RamControllerV2 {

    @Autowired
    private RamService service;

    @Autowired
    private RamModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Memoria RAM")
    public ResponseEntity<EntityModel<RamResponseDTO>> crearRam(@Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO nuevaRam = service.crearRam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevaRam));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de Memorias RAM")
    public ResponseEntity<CollectionModel<EntityModel<RamResponseDTO>>> listarRams() {
        List<EntityModel<RamResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(RamControllerV2.class).listarRams()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar RAM por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RamResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 10, \"tipo\": \"DDR5\", \"capacidad\": 32, \"frecuencia\": 5600}")))
    })
    public ResponseEntity<EntityModel<RamResponseDTO>> obtenerRamPorId(
            @Parameter(description = "Id de la Memoria RAM", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar RAM")
    public ResponseEntity<EntityModel<RamResponseDTO>> actualizarRam(@PathVariable Long id, @Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO ramActualizada = service.actualizarRam(id, request);
        return ResponseEntity.ok(assembler.toModel(ramActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar RAM")
    public ResponseEntity<Void> eliminarRam(@PathVariable Long id) {
        service.eliminarRam(id);
        return ResponseEntity.noContent().build();
    }
}