package com.buildmypc.gpu_service.controller;

import com.buildmypc.gpu_service.assemblers.GpuModelAssembler;
import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.service.GpuService;
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
@RequestMapping("/api/v2/gpus")
@Validated
@Tag(name="GPU V2", description = "API HATEOAS para la gestión técnica de Tarjetas Gráficas.")
public class GpuControllerV2 {

    @Autowired
    private GpuService service;

    @Autowired
    private GpuModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar GPU")
    public ResponseEntity<EntityModel<GpuResponseDTO>> crearGpu(@Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO nueva = service.crearGpu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de GPUs")
    public ResponseEntity<CollectionModel<EntityModel<GpuResponseDTO>>> listarGpus() {
        List<EntityModel<GpuResponseDTO>> entities = service.obtenerTodas().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(GpuControllerV2.class).listarGpus()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar GPU por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GpuResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 22, \"vram\": 12, \"consumoWatts\": 320, \"frecuencia\": 2500}")))
    })
    public ResponseEntity<EntityModel<GpuResponseDTO>> obtenerGpuPorId(
            @Parameter(description = "Id de la GPU", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar GPU")
    public ResponseEntity<EntityModel<GpuResponseDTO>> actualizarGpu(@PathVariable Long id, @Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO actualizada = service.actualizarGpu(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar GPU")
    public ResponseEntity<Void> eliminarGpu(@PathVariable Long id) {
        service.eliminarGpu(id);
        return ResponseEntity.noContent().build();
    }
}