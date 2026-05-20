package com.buildmypc.psu_service.dto;
import lombok.Data;

@Data
public class PsuResponseDTO {
    private Long id;
    private Long componentId;
    private Integer watts;
    private String certificacion;
    private Boolean modular;
}
