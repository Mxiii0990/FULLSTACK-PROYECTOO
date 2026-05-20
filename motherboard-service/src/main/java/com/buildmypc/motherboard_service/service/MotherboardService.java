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

    public MotherboardResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando Placa Madre por ID: " + id);
        Motherboard motherboard = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Placa Madre no encontrada con ID: " + id));
        return mapearAResponseDTO(motherboard);
    }

    public MotherboardResponseDTO actualizarMotherboard(Long id, MotherboardRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando Placa Madre con ID: " + id);
        Motherboard existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Placa Madre no encontrada con ID: " + id));

        existente.setComponentId(request.getComponentId());
        existente.setSocket(request.getSocket());
        existente.setTipoRam(request.getTipoRam());
        existente.setFormato(request.getFormato());

        Motherboard actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarMotherboard(Long id) {
        System.out.println("Ejecutando metodo: Eliminando Placa Madre con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Placa Madre no encontrada con ID: " + id);
        }
        repository.deleteById(id);
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
