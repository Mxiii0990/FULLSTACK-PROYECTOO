package com.buildmypc.cpu_service.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CpuRequestDTO {
    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;

    @NotBlank(message = "El socket es obligatorio")
    private String socket;

    @NotNull(message = "Los nucleos son obligatorios")
    @Min(value = 1, message = "Debe tener minimo 1 nucleo")
    private Integer cores;

    @NotNull(message = "Los hilos son obligatorios")
    @Min(value = 1, message = "Debe tener minimo 1 hilo")
    private Integer threads;

    @NotNull(message = "El TDP es obligatorio")
    private Integer tdp;

    @NotNull(message = "La frecuencia base es obligatoria")
    private Double frecuenciaBase;
}
