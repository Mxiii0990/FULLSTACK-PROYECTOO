package com.buildmypc.compatibility_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ValidacionResponseDTO {
    private Long id;
    private Long buildId;
    private Boolean compatible;
    private Integer consumoEstimadoWatts;
    private Integer margenFuente;
    private String observaciones;
    private LocalDate fechaValidacion;
}