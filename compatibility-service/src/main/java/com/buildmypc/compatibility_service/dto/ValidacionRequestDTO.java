package com.buildmypc.compatibility_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ValidacionRequestDTO {

    @NotNull(message = "El ID de la build es obligatorio")
    private Long buildId;

    @NotNull(message = "Debe indicar si es compatible o no")
    private Boolean compatible;

    @NotNull(message = "El consumo estimado es obligatorio")
    @Min(value = 0, message = "El consumo no puede ser negativo")
    private Integer consumoEstimadoWatts;

    @NotNull(message = "El margen de la fuente es obligatorio")
    private Integer margenFuente;

    @NotBlank(message = "Debe incluir una observación")
    private String observaciones;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaValidacion;
}