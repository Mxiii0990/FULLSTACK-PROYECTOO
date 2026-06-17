package com.buildmypc.builder_service.assemblers;

import com.buildmypc.builder_service.controller.PcBuildControllerV2;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuilderModelAssembler implements RepresentationModelAssembler<PcBuildResponseDTO, EntityModel<PcBuildResponseDTO>> {

    @Override
    public EntityModel<PcBuildResponseDTO> toModel(PcBuildResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(PcBuildControllerV2.class).obtenerEnsamblePorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(PcBuildControllerV2.class).listarEnsambles()).withRel("ensambles")
        );
    }
}