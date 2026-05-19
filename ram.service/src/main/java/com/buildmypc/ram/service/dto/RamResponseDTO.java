package com.buildmypc.ram.service.dto;
import lombok.Data;

@Data
public class RamResponseDTO {
    private Long id;              // ¡Necesitas devolver el ID que generó la base de datos!
    private Long componentId;     // Para saber a qué componente pertenece
    private String tipo;          // DDR4, DDR5...
    private Integer capacidad;    // La cantidad de GB
    private Integer frecuencia;   // La velocidad en MHz
}
