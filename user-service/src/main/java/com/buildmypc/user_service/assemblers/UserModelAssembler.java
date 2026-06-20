package com.buildmypc.user_service.assemblers;

import com.buildmypc.user_service.controller.UserControllerV2;
import com.buildmypc.user_service.dto.UserResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

    @Override
    public EntityModel<UserResponseDTO> toModel(UserResponseDTO dto) {
        return EntityModel.of(
                dto,
                linkTo(methodOn(UserControllerV2.class).obtenerUsuarioPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(UserControllerV2.class).listarUsuarios()).withRel("users")
        );
    }
}