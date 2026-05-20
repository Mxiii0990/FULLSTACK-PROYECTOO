package com.buildmypc.compatibility_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "validaciones")
@Data
public class ValidacionCompatibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buildId; // ID del ensamble que estamos revisando

    private Boolean compatible; // true si todo encaja, false si hay error
    private Integer consumoEstimadoWatts;
    private Integer margenFuente;
    private String observaciones; // Ej: "Socket incorrecto" o "Todo OK"
    private LocalDate fechaValidacion;
}