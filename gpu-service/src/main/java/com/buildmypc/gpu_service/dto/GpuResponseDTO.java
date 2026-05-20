package com.buildmypc.gpu_service.dto;
import lombok.Data;

@Data
public class GpuResponseDTO {
    private Long id;
    private Long componentId;
    private Integer vram;
    private Integer consumoWatts;
    private Integer frecuencia;
}
