package com.buildmypc.psu_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "psus")
@Data
public class Psu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId; // Nos conecta al catálogo principal

    private Integer watts;         // Ej: 650, 750, 850 (Lo más importante para ver si la PC enciende)
    private String certificacion;  // Ej: 80 Plus Gold, 80 Plus Bronze
    private Boolean modular;       // true o false
}
