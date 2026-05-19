package com.buildmypc.ram.service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RamRequestDTO {
    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;
    @NotNull @Min(1) private Integer capacidad;
    @NotNull @Min(1) private Integer frecuencia;
}
