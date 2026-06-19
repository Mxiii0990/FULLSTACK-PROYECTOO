package com.buildmypc.cpu_service.assemblers;

import com.buildmypc.cpu_service.controller.CpuControllerV2;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CpuModelAssembler implements RepresentationModelAssembler<CpuResponseDTO, EntityModel<CpuResponseDTO>> {

    @Override
    public EntityModel<CpuResponseDTO> toModel(CpuResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(CpuControllerV2.class).obtenerCpuPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(CpuControllerV2.class).listarCpus()).withRel("cpus")
        );
    }
}