package com.buildmypc.ram_service.service;
import com.buildmypc.ram_service.dto.RamRequestDTO;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import com.buildmypc.ram_service.model.Ram;
import com.buildmypc.ram_service.repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RamService {
    @Autowired
    private RamRepository repository;

    public RamResponseDTO crearRam(RamRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva RAM");

        Ram ram = new Ram();
        ram.setComponentId(request.getComponentId());
        ram.setTipo(request.getTipo());
        ram.setCapacidad(request.getCapacidad());
        ram.setFrecuencia(request.getFrecuencia());

        Ram guardado = repository.save(ram);
        return mapearAResponseDTO(guardado);
    }

    public List<RamResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las RAMs");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    private RamResponseDTO mapearAResponseDTO(Ram ram) {
        RamResponseDTO dto = new RamResponseDTO();
        dto.setId(ram.getId());
        dto.setComponentId(ram.getComponentId());
        dto.setTipo(ram.getTipo());
        dto.setCapacidad(ram.getCapacidad());
        dto.setFrecuencia(ram.getFrecuencia());
        return dto;
    }
}
