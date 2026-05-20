package com.buildmypc.psu_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PsuRequestDTO {

    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;

    @NotNull(message = "La capacidad en Watts es obligatoria")
    @Min(value = 300, message = "La fuente debe ser de al menos 300 Watts")
    private Integer watts;

    @NotBlank(message = "La certificacion es obligatoria")
    private String certificacion;

    @NotNull(message = "Debe indicar si es modular o no (true/false)")
    private Boolean modular;
}
