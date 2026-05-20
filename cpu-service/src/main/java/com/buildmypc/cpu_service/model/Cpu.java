package com.buildmypc.cpu_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cpus")
@Data
public class Cpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId; // Conecta con el ID del component-service

    private String socket;
    private Integer cores;
    private Integer threads;
    private Integer tdp; // Consumo en Watts
    private Double frecuenciaBase;
}
