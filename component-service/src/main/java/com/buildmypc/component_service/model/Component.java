package com.buildmypc.component_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "components")
//esta clase debe convertirse en una tabla llamada componentes en la base de datos Oracle
@Data
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //para que la base de datos asigne el número automáticamente (auto-incremental)
    private Long id;

    private String tipo;
    private String marca;
    private String modelo;
    private double precioBase;
    private String estado;
    private String descripcion;
    private LocalDate fechaLanzamiento;

}
