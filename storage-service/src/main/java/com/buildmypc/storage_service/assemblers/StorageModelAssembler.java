package com.buildmypc.storage_service.assemblers;

import com.buildmypc.storage_service.controller.StorageControllerV2;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StorageModelAssembler implements RepresentationModelAssembler<StorageResponseDTO, EntityModel<StorageResponseDTO>> {

    @Override
    public EntityModel<StorageResponseDTO> toModel(StorageResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(StorageControllerV2.class).obtenerStoragePorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(StorageControllerV2.class).listarStorages()).withRel("storages")
        );
    }
}