package com.buildmypc.component_service.service;
import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.model.Component;
import com.buildmypc.component_service.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ComponentService {
    @Autowired
    private ComponentRepository repository;

    public List<ComponentResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Obteniendo todos los components del catalogo");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public ComponentResponseDTO crearComponent(ComponentRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nuevo component de marca " + request.getMarca());

        Component component = new Component();
        component.setTipo(request.getTipo());
        component.setMarca(request.getMarca());
        component.setModelo(request.getModelo());
        component.setPrecioBase(request.getPrecioBase());
        component.setEstado(request.getEstado());
        component.setDescripcion(request.getDescripcion());

        Component guardado = repository.save(component);
        return mapearAResponseDTO(guardado);
    }

    private ComponentResponseDTO mapearAResponseDTO(Component component) {
        ComponentResponseDTO dto = new ComponentResponseDTO();
        dto.setId(component.getId());
        dto.setTipo(component.getTipo());
        dto.setMarca(component.getMarca());
        dto.setModelo(component.getModelo());
        dto.setPrecioBase(component.getPrecioBase());
        dto.setEstado(component.getEstado());
        dto.setDescripcion(component.getDescripcion());
        return dto;
    }
}
