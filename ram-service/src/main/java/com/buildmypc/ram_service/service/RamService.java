package com.buildmypc.ram_service.service;

import com.buildmypc.ram_service.client.ComponentClient; // <-- Importante: Importamos el cliente
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

    @Autowired
    private ComponentClient componentClient; // <-- Inyectamos la conexion al catalogo

    public RamResponseDTO crearRam(RamRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva RAM");

        // --- VALIDACION OPENFEIGN ---
        // Pregunta al puerto 8081 si existe el componente base. Si no, explota y no guarda.
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

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

    public RamResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando RAM por ID: " + id);
        Ram ram = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RAM no encontrada con ID: " + id));
        return mapearAResponseDTO(ram);
    }

    public RamResponseDTO actualizarRam(Long id, RamRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando RAM con ID: " + id);
        Ram existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RAM no encontrada con ID: " + id));

        // --- VALIDACION OPENFEIGN ---
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        existente.setComponentId(request.getComponentId());
        existente.setTipo(request.getTipo());
        existente.setCapacidad(request.getCapacidad());
        existente.setFrecuencia(request.getFrecuencia());

        Ram actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarRam(Long id) {
        System.out.println("Ejecutando metodo: Eliminando RAM con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("RAM no encontrada con ID: " + id);
        }
        repository.deleteById(id);
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