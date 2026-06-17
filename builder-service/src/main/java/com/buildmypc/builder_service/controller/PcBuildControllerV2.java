package com.buildmypc.builder_service.controller;

import com.buildmypc.builder_service.assemblers.BuilderModelAssembler;
import com.buildmypc.builder_service.dto.PcBuildRequestDTO;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import com.buildmypc.builder_service.service.PcBuildService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/V2/builds")
@Validated
@Tag(name="Builder V2", description = "Metodos CRUD para la gestión de ensambles de PC")
public class PcBuildControllerV2 {

    @Autowired
    private PcBuildService service;

    // El assembler arma los enlaces HATEOAS de cada ensamble.
    @Autowired
    private BuilderModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listado de todos los ensambles",
            description = "Se devuelve una lista con los ensambles que se encuentran en la DB"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<CollectionModel<EntityModel<PcBuildResponseDTO>>> listarEnsambles() {
        // 1) Traer ensambles y convertir cada uno en EntityModel (ensamble + sus enlaces).
        List<EntityModel<PcBuildResponseDTO>> entityModels = this.service.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .toList();
        // 2) Envolver la lista en un CollectionModel y agregarle su propio enlace self.
        CollectionModel<EntityModel<PcBuildResponseDTO>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(PcBuildControllerV2.class).listarEnsambles()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busqueda de un ensamble",
            description = "Se devuelve un ensamble, en caso contrario se devuelve una excepcion"
    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Ensamble encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PcBuildResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo Ensamble",
                                            value = "{\"id\": 1, \"nombreEnsamble\": \"PC Master Race 2026\", \"cpuId\": 1, \"motherboardId\": 2}"
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Ensamble no se encuentra en la BD")
    })
    public ResponseEntity<EntityModel<PcBuildResponseDTO>> obtenerEnsamblePorId(
            @Parameter(description = "Id del ensamble a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        // Buscar el ensamble y pedirle al assembler que le agregue los enlaces.
        EntityModel<PcBuildResponseDTO> entityModel = this.assembler.toModel(
                this.service.obtenerPorId(id)
        );
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(summary = "Guardado de ensamble", description = "Esta es la forma de guardar un ensamble")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Ensamble a crear", required = true,
            content = @Content(schema = @Schema(implementation = PcBuildRequestDTO.class))
    )
    public ResponseEntity<EntityModel<PcBuildResponseDTO>> crearEnsamble(@Valid @RequestBody PcBuildRequestDTO request) {
        PcBuildResponseDTO nuevo = this.service.crearEnsamble(request);
        EntityModel<PcBuildResponseDTO> entityModel = this.assembler.toModel(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion de ensamble", description = "Se actualizan los datos de un ensamble existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ensamble actualizado"),
            @ApiResponse(responseCode = "404", description = "Ensamble no se encuentra en la BD")
    })
    public ResponseEntity<EntityModel<PcBuildResponseDTO>> actualizarEnsamble(
            @Parameter(description = "Id del ensamble a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PcBuildRequestDTO request
    ) {
        PcBuildResponseDTO actualizado = this.service.actualizarEnsamble(id, request);
        EntityModel<PcBuildResponseDTO> entityModel = this.assembler.toModel(actualizado);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion de ensamble", description = "Se elimina un ensamble de forma permanente")
    @ApiResponse(responseCode = "204", description = "Ensamble eliminado")
    public ResponseEntity<Void> eliminarEnsamble(
            @Parameter(description = "Id del ensamble a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.service.eliminarEnsamble(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}