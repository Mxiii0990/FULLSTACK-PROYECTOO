package com.buildmypc.builder_service.assemblers;

import com.buildmypc.builder_service.controller.PcBuildControllerV2;
import com.buildmypc.builder_service.model.PcBuild;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuilderModelAssembler implements RepresentationModelAssembler<PcBuild, EntityModel<PcBuild>> {

    @Override
    public EntityModel<PcBuild> toModel(PcBuild pcBuild) {
        return EntityModel.of(
                pcBuild,
                linkTo(methodOn(PcBuildControllerV2.class).obtenerEnsamblePorId(pcBuild.getId())).withSelfRel(),
                linkTo(methodOn(PcBuildControllerV2.class).listarEnsambles()).withRel("ensambles")
        );
    }
}