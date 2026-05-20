package com.buildmypc.component_service.service;
import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.model.Component;
import com.buildmypc.component_service.repository.ComponentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ComponentService {
    @Autowired
    private ComponentRepository repository;

    public ComponentResponseDTO crearComponente(ComponentRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nuevo componente de catalogo");
        Component component = new Component();


        component.setNombre(request.getNombre());
        component.setTipo(request.getTipo());
        component.setMarca(request.getMarca());
        component.setModelo(request.getModelo());
        component.setPrecio(request.getPrecio());
        component.setEstado(request.getEstado());
        component.setDescripcion(request.getDescripcion());
        component.setFechaLanzamiento(request.getFechaLanzamiento());

        Component guardado = repository.save(component);
        return mapearAResponseDTO(guardado);
    }

    public List<ComponentResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Listando todos los componentes");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public ComponentResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando componente por ID: " + id);
        Component component = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Componente no encontrado con ID: " + id));
        return mapearAResponseDTO(component);
    }

    public ComponentResponseDTO actualizarComponente(Long id, ComponentRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando componente con ID: " + id);
        Component existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Componente no encontrado con ID: " + id));


        existente.setNombre(request.getNombre());
        existente.setTipo(request.getTipo());
        existente.setMarca(request.getMarca());
        existente.setModelo(request.getModelo());
        existente.setPrecio(request.getPrecio());
        existente.setEstado(request.getEstado());
        existente.setDescripcion(request.getDescripcion());
        existente.setFechaLanzamiento(request.getFechaLanzamiento());

        Component actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarComponente(Long id) {
        System.out.println("Ejecutando metodo: Eliminando componente con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Componente no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    private ComponentResponseDTO mapearAResponseDTO(Component component) {
        ComponentResponseDTO dto = new ComponentResponseDTO();


        dto.setId(component.getId());
        dto.setNombre(component.getNombre());
        dto.setTipo(component.getTipo());
        dto.setMarca(component.getMarca());
        dto.setModelo(component.getModelo());
        dto.setPrecio(component.getPrecio());
        dto.setEstado(component.getEstado());
        dto.setDescripcion(component.getDescripcion());
        dto.setFechaLanzamiento(component.getFechaLanzamiento());

        return dto;
    }
}
