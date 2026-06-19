package com.buildmypc.component_service.assemblers;

import com.buildmypc.component_service.controller.ComponentControllerV2;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComponentModelAssembler implements RepresentationModelAssembler<ComponentResponseDTO, EntityModel<ComponentResponseDTO>> {

    @Override
    public EntityModel<ComponentResponseDTO> toModel(ComponentResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(ComponentControllerV2.class).obtenerComponentePorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ComponentControllerV2.class).listarComponentes()).withRel("componentes")
        );
    }
}