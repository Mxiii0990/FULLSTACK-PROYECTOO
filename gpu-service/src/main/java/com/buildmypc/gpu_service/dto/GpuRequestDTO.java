package com.buildmypc.gpu_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class GpuRequestDTO {
    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;

    @NotNull(message = "La VRAM es obligatoria")
    @Min(value = 1, message = "La VRAM debe ser mayor a 0")
    private Integer vram;

    @NotNull(message = "El consumo en Watts es obligatorio")
    @Min(value = 1, message = "El consumo debe ser mayor a 0")
    private Integer consumoWatts;

    @NotNull(message = "La frecuencia es obligatoria")
    private Integer frecuencia;
}
