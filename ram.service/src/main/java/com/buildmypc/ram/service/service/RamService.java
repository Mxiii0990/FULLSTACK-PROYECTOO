package com.buildmypc.ram.service.service;
import com.buildmypc.ram.service.dto.RamRequestDTO;
import com.buildmypc.ram.service.dto.RamResponseDTO;
import com.buildmypc.ram.service.model.Ram;
import com.buildmypc.ram.service.repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RamService {
    @Autowired
    private RamRepository repository;

    // MÉTODO 1: Para el POST (Crear)
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

    // MÉTODO 2: Para el GET (Listar)
    public List<RamResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las RAMs");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    // Método privado para mapear
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
