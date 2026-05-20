package com.buildmypc.ram_service.dto;
import lombok.Data;

@Data
public class RamResponseDTO {
    private Long id;
    private Long componentId;
    private String tipo;
    private Integer capacidad;
    private Integer frecuencia;
}
