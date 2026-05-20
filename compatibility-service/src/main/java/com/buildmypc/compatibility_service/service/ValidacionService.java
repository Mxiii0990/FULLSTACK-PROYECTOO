package com.buildmypc.compatibility_service.service;

import com.buildmypc.compatibility_service.dto.ValidacionRequestDTO;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import com.buildmypc.compatibility_service.model.ValidacionCompatibilidad;
import com.buildmypc.compatibility_service.repository.ValidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidacionService {

    @Autowired
    private ValidacionRepository repository;

    public ValidacionResponseDTO crearValidacion(ValidacionRequestDTO request) {
        System.out.println("Ejecutando metodo: Guardando resultado de compatibilidad");
        ValidacionCompatibilidad validacion = new ValidacionCompatibilidad();
        validacion.setBuildId(request.getBuildId());
        validacion.setCompatible(request.getCompatible());
        validacion.setConsumoEstimadoWatts(request.getConsumoEstimadoWatts());
        validacion.setMargenFuente(request.getMargenFuente());
        validacion.setObservaciones(request.getObservaciones());
        validacion.setFechaValidacion(request.getFechaValidacion());

        ValidacionCompatibilidad guardado = repository.save(validacion);
        return mapearAResponseDTO(guardado);
    }

    public List<ValidacionResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando historial de validaciones");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public ValidacionResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando validacion por ID: " + id);
        ValidacionCompatibilidad validacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Validacion no encontrada con ID: " + id));
        return mapearAResponseDTO(validacion);
    }

    public ValidacionResponseDTO actualizarValidacion(Long id, ValidacionRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando validacion con ID: " + id);
        ValidacionCompatibilidad existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Validacion no encontrada con ID: " + id));

        existente.setBuildId(request.getBuildId());
        existente.setCompatible(request.getCompatible());
        existente.setConsumoEstimadoWatts(request.getConsumoEstimadoWatts());
        existente.setMargenFuente(request.getMargenFuente());
        existente.setObservaciones(request.getObservaciones());
        existente.setFechaValidacion(request.getFechaValidacion());

        ValidacionCompatibilidad actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarValidacion(Long id) {
        System.out.println("Ejecutando metodo: Borrando validacion con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Validacion no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private ValidacionResponseDTO mapearAResponseDTO(ValidacionCompatibilidad validacion) {
        ValidacionResponseDTO dto = new ValidacionResponseDTO();
        dto.setId(validacion.getId());
        dto.setBuildId(validacion.getBuildId());
        dto.setCompatible(validacion.getCompatible());
        dto.setConsumoEstimadoWatts(validacion.getConsumoEstimadoWatts());
        dto.setMargenFuente(validacion.getMargenFuente());
        dto.setObservaciones(validacion.getObservaciones());
        dto.setFechaValidacion(validacion.getFechaValidacion());
        return dto;
    }
}