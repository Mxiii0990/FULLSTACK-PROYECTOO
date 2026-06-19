package com.buildmypc.compatibility_service.assemblers;

import com.buildmypc.compatibility_service.controller.ValidacionControllerV2;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CompatibilityModelAssembler implements RepresentationModelAssembler<ValidacionResponseDTO, EntityModel<ValidacionResponseDTO>> {

    @Override
    public EntityModel<ValidacionResponseDTO> toModel(ValidacionResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(ValidacionControllerV2.class).obtenerValidacionPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ValidacionControllerV2.class).listarValidaciones()).withRel("validaciones")
        );
    }
}