package com.buildmypc.component_service.controller;

import com.buildmypc.component_service.assemblers.ComponentModelAssembler;
import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.service.ComponentService;
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
@RequestMapping("/api/v2/components")
@Validated
@Tag(name="Componentes V2", description = "API HATEOAS para gestión del catálogo.")
public class ComponentControllerV2 {

    @Autowired
    private ComponentService service;

    @Autowired
    private ComponentModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar componente")
    public ResponseEntity<EntityModel<ComponentResponseDTO>> crearComponente(@Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO nuevo = service.crearComponente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de componentes")
    public ResponseEntity<CollectionModel<EntityModel<ComponentResponseDTO>>> listarComponentes() {
        List<EntityModel<ComponentResponseDTO>> entities = service.obtenerTodos().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(ComponentControllerV2.class).listarComponentes()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar componente por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ComponentResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"nombre\": \"Ryzen 5 7600X\", \"tipo\": \"CPU\", \"marca\": \"AMD\", \"modelo\": \"100-100000593WOF\", \"precio\": 250.0, \"estado\": \"NUEVO\", \"descripcion\": \"Procesador AM5\", \"fechaLanzamiento\": \"2023-01-15\"}")))
    })
    public ResponseEntity<EntityModel<ComponentResponseDTO>> obtenerComponentePorId(
            @Parameter(description = "Id del componente", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar componente")
    public ResponseEntity<EntityModel<ComponentResponseDTO>> actualizarComponente(@PathVariable Long id, @Valid @RequestBody ComponentRequestDTO request) {
        ComponentResponseDTO actualizado = service.actualizarComponente(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar componente")
    public ResponseEntity<Void> eliminarComponente(@PathVariable Long id) {
        service.eliminarComponente(id);
        return ResponseEntity.noContent().build();
    }
}