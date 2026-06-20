package com.buildmypc.motherboard_service.controller;

import com.buildmypc.motherboard_service.assemblers.MotherboardModelAssembler;
import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.service.MotherboardService;
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
@RequestMapping("/api/v2/motherboards")
@Validated
@Tag(name="Motherboard V2", description = "API HATEOAS para la gestión técnica de Placas Madres.")
public class MotherboardControllerV2 {

    @Autowired
    private MotherboardService service;

    @Autowired
    private MotherboardModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Placa Madre")
    public ResponseEntity<EntityModel<MotherboardResponseDTO>> crearMotherboard(@Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO nueva = service.crearMotherboard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de Placas Madres")
    public ResponseEntity<CollectionModel<EntityModel<MotherboardResponseDTO>>> listarMotherboards() {
        List<EntityModel<MotherboardResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(MotherboardControllerV2.class).listarMotherboards()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Placa Madre por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MotherboardResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 18, \"socket\": \"AM5\", \"tipoRam\": \"DDR5\", \"formato\": \"ATX\"}")))
    })
    public ResponseEntity<EntityModel<MotherboardResponseDTO>> obtenerMotherboardPorId(
            @Parameter(description = "Id de la Placa Madre", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Placa Madre")
    public ResponseEntity<EntityModel<MotherboardResponseDTO>> actualizarMotherboard(@PathVariable Long id, @Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO actualizada = service.actualizarMotherboard(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Placa Madre")
    public ResponseEntity<Void> eliminarMotherboard(@PathVariable Long id) {
        service.eliminarMotherboard(id);
        return ResponseEntity.noContent().build();
    }
}