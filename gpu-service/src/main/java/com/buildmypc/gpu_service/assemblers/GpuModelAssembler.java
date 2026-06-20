package com.buildmypc.gpu_service.assemblers;

import com.buildmypc.gpu_service.controller.GpuControllerV2;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GpuModelAssembler implements RepresentationModelAssembler<GpuResponseDTO, EntityModel<GpuResponseDTO>> {

    @Override
    public EntityModel<GpuResponseDTO> toModel(GpuResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(GpuControllerV2.class).obtenerGpuPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(GpuControllerV2.class).listarGpus()).withRel("gpus")
        );
    }
}