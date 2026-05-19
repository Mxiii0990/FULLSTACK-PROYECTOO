package com.buildmypc.cpu_service.service;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.model.Cpu;
import com.buildmypc.cpu_service.repository.CpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CpuService {
    @Autowired
    private CpuRepository repository;

    public List<CpuResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Obteniendo todas las CPUs del catalogo");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public CpuResponseDTO crearCpu(CpuRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva CPU vinculada al Component ID: " + request.getComponentId());

        Cpu cpu = new Cpu();
        cpu.setComponentId(request.getComponentId());
        cpu.setSocket(request.getSocket());
        cpu.setCores(request.getCores());
        cpu.setThreads(request.getThreads());
        cpu.setTdp(request.getTdp());

        Cpu guardado = repository.save(cpu);
        return mapearAResponseDTO(guardado);
    }

    private CpuResponseDTO mapearAResponseDTO(Cpu cpu) {
        CpuResponseDTO dto = new CpuResponseDTO();
        dto.setId(cpu.getId());
        dto.setComponentId(cpu.getComponentId());
        dto.setSocket(cpu.getSocket());
        dto.setCores(cpu.getCores());
        dto.setThreads(cpu.getThreads());
        dto.setTdp(cpu.getTdp());
        return dto;
    }
}
