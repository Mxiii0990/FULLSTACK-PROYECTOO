package com.buildmypc.builder_service.dto;
import lombok.Data;

@Data
public class PcBuildResponseDTO {
    private Long id;
    private String nombreEnsamble;
    private Long cpuId;
    private Long motherboardId;
    private Long ramId;
    private Long gpuId;
    private Long storageId;
    private Long psuId;
}
