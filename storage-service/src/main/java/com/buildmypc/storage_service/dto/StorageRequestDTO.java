package com.buildmypc.storage_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StorageRequestDTO {

    @NotNull(message = "El ID del componente es obligatorio")
    private Long componentId;

    @NotBlank(message = "El tipo de almacenamiento es obligatorio")
    private String tipo;

    @NotNull(message = "La capacidad en GB es obligatoria")
    @Min(value = 120, message = "La capacidad minima es 120GB")
    private Integer capacidadGb;

    @NotNull(message = "La velocidad de lectura es obligatoria")
    @Min(value = 50, message = "La velocidad de lectura debe ser valida")
    private Integer velocidadLectura;
}
