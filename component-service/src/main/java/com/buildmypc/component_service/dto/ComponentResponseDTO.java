package com.buildmypc.component_service.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ComponentResponseDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String marca;
    private String modelo;
    private double precio;
    private String estado;
    private String descripcion;
    private LocalDate fechaLanzamiento;
}
