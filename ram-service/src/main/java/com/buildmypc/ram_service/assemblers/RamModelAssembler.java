package com.buildmypc.ram_service.assemblers;

import com.buildmypc.ram_service.controller.RamControllerV2;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RamModelAssembler implements RepresentationModelAssembler<RamResponseDTO, EntityModel<RamResponseDTO>> {

    @Override
    public EntityModel<RamResponseDTO> toModel(RamResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(RamControllerV2.class).obtenerRamPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(RamControllerV2.class).listarRams()).withRel("rams")
        );
    }
}