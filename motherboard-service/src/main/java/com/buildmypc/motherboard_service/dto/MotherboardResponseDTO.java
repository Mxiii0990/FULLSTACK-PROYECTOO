package com.buildmypc.motherboard_service.dto;
import lombok.Data;

@Data
public class MotherboardResponseDTO {
    private Long id;
    private Long componentId;
    private String socket;
    private String tipoRam;
    private String formato;
}
