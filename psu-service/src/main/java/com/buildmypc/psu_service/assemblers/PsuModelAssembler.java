package com.buildmypc.psu_service.assemblers;

import com.buildmypc.psu_service.controller.PsuControllerV2;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PsuModelAssembler implements RepresentationModelAssembler<PsuResponseDTO, EntityModel<PsuResponseDTO>> {

    @Override
    public EntityModel<PsuResponseDTO> toModel(PsuResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(PsuControllerV2.class).obtenerPsuPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(PsuControllerV2.class).listarPsus()).withRel("psus")
        );
    }
}