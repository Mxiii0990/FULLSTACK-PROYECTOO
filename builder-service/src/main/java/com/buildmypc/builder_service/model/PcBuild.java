package com.buildmypc.builder_service.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pc_builds")
@Data
public class PcBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreEnsamble;
    private Long cpuId;
    private Long motherboardId;
    private Long ramId;
    private Long gpuId;
    private Long storageId;
    private Long psuId;
}
