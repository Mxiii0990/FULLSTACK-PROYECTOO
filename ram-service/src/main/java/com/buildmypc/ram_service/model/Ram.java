package com.buildmypc.ram_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rams")
@Data
public class Ram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId;

    private String tipo;      // Ej: DDR4, DDR5
    private Integer capacidad; // Ej: 16, 32
    private Integer frecuencia; // Ej: 3200, 5600
}
