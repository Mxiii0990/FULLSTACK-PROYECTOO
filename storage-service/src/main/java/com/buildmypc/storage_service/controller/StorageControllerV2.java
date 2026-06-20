package com.buildmypc.storage_service.controller;

import com.buildmypc.storage_service.assemblers.StorageModelAssembler;
import com.buildmypc.storage_service.dto.StorageRequestDTO;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import com.buildmypc.storage_service.service.StorageService;
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
@RequestMapping("/api/v2/storages")
@Validated
@Tag(name="Storage V2", description = "API HATEOAS para la gestión técnica de Almacenamiento.")
public class StorageControllerV2 {

    @Autowired
    private StorageService service;

    @Autowired
    private StorageModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Unidad de Almacenamiento")
    public ResponseEntity<EntityModel<StorageResponseDTO>> crearStorage(@Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO nuevo = service.crearStorage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de Almacenamiento")
    public ResponseEntity<CollectionModel<EntityModel<StorageResponseDTO>>> listarStorages() {
        List<EntityModel<StorageResponseDTO>> entities = service.obtenerTodos().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(StorageControllerV2.class).listarStorages()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Almacenamiento por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StorageResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"componentId\": 105, \"tipo\": \"SSD NVMe\", \"capacidadGb\": 1000, \"velocidadLectura\": 3500}")))
    })
    public ResponseEntity<EntityModel<StorageResponseDTO>> obtenerStoragePorId(
            @Parameter(description = "Id del Almacenamiento", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Almacenamiento")
    public ResponseEntity<EntityModel<StorageResponseDTO>> actualizarStorage(@PathVariable Long id, @Valid @RequestBody StorageRequestDTO request) {
        StorageResponseDTO actualizado = service.actualizarStorage(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Almacenamiento")
    public ResponseEntity<Void> eliminarStorage(@PathVariable Long id) {
        service.eliminarStorage(id);
        return ResponseEntity.noContent().build();
    }
}