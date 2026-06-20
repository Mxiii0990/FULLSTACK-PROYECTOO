package com.buildmypc.user_service.controller;

import com.buildmypc.user_service.assemblers.UserModelAssembler;
import com.buildmypc.user_service.dto.UserRequestDTO;
import com.buildmypc.user_service.dto.UserResponseDTO;
import com.buildmypc.user_service.service.UserService;
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
@RequestMapping("/api/v2/users")
@Validated
@Tag(name="Usuarios V2", description = "API HATEOAS para gestión de identidades.")
public class UserControllerV2 {

    @Autowired
    private UserService service;

    @Autowired
    private UserModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Usuario")
    public ResponseEntity<EntityModel<UserResponseDTO>> crearUsuario(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO nuevo = service.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    @GetMapping
    @Operation(summary = "Listado HATEOAS de Usuarios")
    public ResponseEntity<CollectionModel<EntityModel<UserResponseDTO>>> listarUsuarios() {
        List<EntityModel<UserResponseDTO>> entities = service.obtenerTodos().stream()
                .map(assembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(entities,
                linkTo(methodOn(UserControllerV2.class).listarUsuarios()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuario por ID con enlaces")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"nombre\": \"Martina Silva\", \"correo\": \"martina@buildmypc.cl\", \"rol\": \"CLIENTE\"}")))
    })
    public ResponseEntity<EntityModel<UserResponseDTO>> obtenerUsuarioPorId(
            @Parameter(description = "Id del Usuario", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Usuario")
    public ResponseEntity<EntityModel<UserResponseDTO>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO actualizado = service.actualizarUsuario(id, request);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Usuario")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}