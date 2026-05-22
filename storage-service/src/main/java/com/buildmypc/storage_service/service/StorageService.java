package com.buildmypc.storage_service.service;

import com.buildmypc.storage_service.client.ComponentClient; // <-- Importante: Importamos el cliente
import com.buildmypc.storage_service.dto.StorageRequestDTO;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import com.buildmypc.storage_service.model.Storage;
import com.buildmypc.storage_service.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
    private ComponentClient componentClient; // <-- Inyectamos la conexión al catálogo

    public StorageResponseDTO crearStorage(StorageRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva unidad de Almacenamiento");

        // --- VALIDACION OPENFEIGN ---
        // Pregunta al puerto 8081 si existe el componente base. Si no, lanza error y no guarda.
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        Storage storage = new Storage();
        storage.setComponentId(request.getComponentId());
        storage.setTipo(request.getTipo());
        storage.setCapacidadGb(request.getCapacidadGb());
        storage.setVelocidadLectura(request.getVelocidadLectura());

        Storage guardado = repository.save(storage);
        return mapearAResponseDTO(guardado);
    }

    public List<StorageResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Listando todas las unidades de Almacenamiento");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public StorageResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando almacenamiento por ID: " + id);
        Storage storage = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad de almacenamiento no encontrada con ID: " + id));
        return mapearAResponseDTO(storage);
    }

    public StorageResponseDTO actualizarStorage(Long id, StorageRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando almacenamiento con ID: " + id);
        Storage existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad de almacenamiento no encontrada con ID: " + id));

        // --- VALIDACION OPENFEIGN ---
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        existente.setComponentId(request.getComponentId());
        existente.setTipo(request.getTipo());
        existente.setCapacidadGb(request.getCapacidadGb());
        existente.setVelocidadLectura(request.getVelocidadLectura());

        Storage actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarStorage(Long id) {
        System.out.println("Ejecutando metodo: Eliminando almacenamiento con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Unidad de almacenamiento no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private StorageResponseDTO mapearAResponseDTO(Storage storage) {
        StorageResponseDTO dto = new StorageResponseDTO();
        dto.setId(storage.getId());
        dto.setComponentId(storage.getComponentId());
        dto.setTipo(storage.getTipo());
        dto.setCapacidadGb(storage.getCapacidadGb());
        dto.setVelocidadLectura(storage.getVelocidadLectura());
        return dto;
    }
}