package com.buildmypc.cpu_service.dto;
import lombok.Data;

@Data
public class CpuRespondeDTO {
    private Long id;
    private Long componentId;
    private String socket;
    private Integer cores;
    private Integer threads;
    private Integer tdp;
}
