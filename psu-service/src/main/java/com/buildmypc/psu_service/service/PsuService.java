package com.buildmypc.psu_service.service;

import com.buildmypc.psu_service.client.ComponentClient; // <-- Importante: Importamos el cliente
import com.buildmypc.psu_service.dto.PsuRequestDTO;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import com.buildmypc.psu_service.model.Psu;
import com.buildmypc.psu_service.repository.PsuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PsuService {

    @Autowired
    private PsuRepository repository;

    @Autowired
    private ComponentClient componentClient; // <-- Inyectamos la conexión al catálogo

    public PsuResponseDTO crearPsu(PsuRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva Fuente de Poder");

        // --- VALIDACION OPENFEIGN ---
        // Pregunta al puerto 8081 si existe el componente base. Si no, lanza error y no guarda.
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        Psu psu = new Psu();
        psu.setComponentId(request.getComponentId());
        psu.setWatts(request.getWatts());
        psu.setCertificacion(request.getCertificacion());
        psu.setModular(request.getModular());

        Psu guardado = repository.save(psu);
        return mapearAResponseDTO(guardado);
    }

    public List<PsuResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las Fuentes de Poder");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public PsuResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando Fuente de Poder por ID: " + id);
        Psu psu = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuente de Poder no encontrada con ID: " + id));
        return mapearAResponseDTO(psu);
    }

    public PsuResponseDTO actualizarPsu(Long id, PsuRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando Fuente de Poder con ID: " + id);
        Psu existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuente de Poder no encontrada con ID: " + id));

        // --- VALIDACION OPENFEIGN ---
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        existente.setComponentId(request.getComponentId());
        existente.setWatts(request.getWatts());
        existente.setCertificacion(request.getCertificacion());
        existente.setModular(request.getModular());

        Psu actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarPsu(Long id) {
        System.out.println("Ejecutando metodo: Eliminando Fuente de Poder con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Fuente de Poder no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private PsuResponseDTO mapearAResponseDTO(Psu psu) {
        PsuResponseDTO dto = new PsuResponseDTO();
        dto.setId(psu.getId());
        dto.setComponentId(psu.getComponentId());
        dto.setWatts(psu.getWatts());
        dto.setCertificacion(psu.getCertificacion());
        dto.setModular(psu.getModular());
        return dto;
    }
}