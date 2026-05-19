package com.buildmypc.ram.service.model;
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

    private String tipo;      // DDR4, DDR5
    private Integer capacidad;
    private Integer frecuencia;
}
