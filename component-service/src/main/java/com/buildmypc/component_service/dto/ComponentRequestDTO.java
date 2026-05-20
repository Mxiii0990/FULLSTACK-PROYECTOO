package com.buildmypc.component_service.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data // ¡Esta etiqueta es la que genera el getPrecio() mágicamente!
public class ComponentRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de componente es obligatorio")
    private String tipo; // Ej: CPU, RAM, GPU

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private double precio;

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // Ej: Nuevo, Usado, Reacondicionado

    private String descripcion;

    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    private LocalDate fechaLanzamiento;
}
