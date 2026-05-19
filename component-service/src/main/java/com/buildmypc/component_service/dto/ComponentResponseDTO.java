package com.buildmypc.component_service.dto;
import lombok.Data;


@Data
public class ComponentResponseDTO {
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private Double precioBase;
    private String estado;
    private String descripcion;
}
