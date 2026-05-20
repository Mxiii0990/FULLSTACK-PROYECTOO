package com.buildmypc.storage_service.dto;
import lombok.Data;

@Data
public class StorageResponseDTO {
    private Long id;
    private Long componentId;
    private String tipo;
    private Integer capacidadGb;
    private Integer velocidadLectura;
}
