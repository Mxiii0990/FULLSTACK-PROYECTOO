package com.buildmypc.motherboard_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "motherboards")
@Data
public class Motherboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId; // Conexión con el catálogo general

    private String socket;    // Ej: AM4, LGA1700 (Para validar con la CPU)
    private String tipoRam;   // Ej: DDR4, DDR5 (Para validar con la RAM)
    private String formato;   // Ej: ATX, Micro-ATX
}
