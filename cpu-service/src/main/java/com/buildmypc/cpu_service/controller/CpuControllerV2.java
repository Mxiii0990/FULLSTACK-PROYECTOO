package com.buildmypc.cpu_service.controller;

import com.buildmypc.cpu_service.assemblers.CpuModelAssembler;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.service.CpuService;
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
@RequestMapping("/api/v2/cpus")
@Validated
@Tag(name="CPU V2", description = "API HATEOAS para la gestión técnica de procesadores.")
public class CpuControllerV2 {

    @Autowired
    private CpuService service;

    @Autowired
    private CpuModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar CPU")
    public ResponseEntity<EntityModel<CpuResponseDTO>> crearCpu(@Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO nuevo = service.crearCpu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de CPUs")
    public ResponseEntity<CollectionModel<EntityModel<CpuResponseDTO>>> listarCpus() {
        List<EntityModel<CpuResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(CpuControllerV2.class).listarCpus()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar CPU por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CpuResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 10, \"socket\": \"AM5\", \"cores\": 6, \"threads\": 12, \"tdp\": 105, \"frecuenciaBase\": 4.7}")))
    })
    public ResponseEntity<EntityModel<CpuResponseDTO>> obtenerCpuPorId(
            @Parameter(description = "Id de la CPU", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar CPU")
    public ResponseEntity<EntityModel<CpuResponseDTO>> actualizarCpu(@PathVariable Long id, @Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO actualizado = service.actualizarCpu(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar CPU")
    public ResponseEntity<Void> eliminarCpu(@PathVariable Long id) {
        service.eliminarCpu(id);
        return ResponseEntity.noContent().build();
    }
}