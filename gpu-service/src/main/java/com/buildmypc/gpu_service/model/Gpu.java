package com.buildmypc.gpu_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gpus")
@Data
public class Gpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId;

    private Integer vram;          // Memoria de video en GB (Ej: 8, 12, 16)
    private Integer consumoWatts;  // Consumo para calcular la fuente de poder
    private Integer frecuencia;    // Velocidad en MHz

}
