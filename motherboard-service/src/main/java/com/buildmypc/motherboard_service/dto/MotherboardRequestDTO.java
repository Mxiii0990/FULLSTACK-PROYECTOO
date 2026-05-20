package com.buildmypc.motherboard_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MotherboardRequestDTO {
    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;

    @NotBlank(message = "El socket es obligatorio")
    private String socket;

    @NotBlank(message = "El tipo de RAM es obligatorio")
    private String tipoRam;

    @NotBlank(message = "El formato es obligatorio")
    private String formato;
}
