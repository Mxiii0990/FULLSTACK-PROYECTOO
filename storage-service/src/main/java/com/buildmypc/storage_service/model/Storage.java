package com.buildmypc.storage_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "storages")
@Data
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long componentId; // Conecta con el catálogo general

    private String tipo;           // Ej: SSD NVMe, SSD SATA, HDD
    private Integer capacidadGb;   // Ej: 500, 1000 (1TB), 2000
    private Integer velocidadLectura; // Ej: 3500 (MB/s
}
