package com.buildmypc.motherboard_service.service;
import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.model.Motherboard;
import com.buildmypc.motherboard_service.repository.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotherboardService {
    @Autowired
    private MotherboardRepository repository;

    public MotherboardResponseDTO crearMotherboard(MotherboardRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva Placa Madre");

        Motherboard motherboard = new Motherboard();
        motherboard.setComponentId(request.getComponentId());
        motherboard.setSocket(request.getSocket());
        motherboard.setTipoRam(request.getTipoRam());
        motherboard.setFormato(request.getFormato());

        Motherboard guardado = repository.save(motherboard);
        return mapearAResponseDTO(guardado);
    }

    public List<MotherboardResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las Placas Madres");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    private MotherboardResponseDTO mapearAResponseDTO(Motherboard motherboard) {
        MotherboardResponseDTO dto = new MotherboardResponseDTO();
        dto.setId(motherboard.getId());
        dto.setComponentId(motherboard.getComponentId());
        dto.setSocket(motherboard.getSocket());
        dto.setTipoRam(motherboard.getTipoRam());
        dto.setFormato(motherboard.getFormato());
        return dto;
    }
}
