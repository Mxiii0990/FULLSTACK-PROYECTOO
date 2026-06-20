package com.buildmypc.motherboard_service.assemblers;

import com.buildmypc.motherboard_service.controller.MotherboardControllerV2;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MotherboardModelAssembler implements RepresentationModelAssembler<MotherboardResponseDTO, EntityModel<MotherboardResponseDTO>> {

    @Override
    public EntityModel<MotherboardResponseDTO> toModel(MotherboardResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(MotherboardControllerV2.class).obtenerMotherboardPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(MotherboardControllerV2.class).listarMotherboards()).withRel("motherboards")
        );
    }
}