package com.buildmypc.gpu_service.service;
import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.model.Gpu;
import com.buildmypc.gpu_service.repository.GpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GpuService {

    @Autowired
    private GpuRepository repository;

    public GpuResponseDTO crearGpu(GpuRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva Tarjeta Grafica (GPU)");
        Gpu gpu = new Gpu();
        gpu.setComponentId(request.getComponentId());
        gpu.setVram(request.getVram());
        gpu.setConsumoWatts(request.getConsumoWatts());
        gpu.setFrecuencia(request.getFrecuencia());

        Gpu guardado = repository.save(gpu);
        return mapearAResponseDTO(guardado);
    }

    public List<GpuResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las Tarjetas Graficas");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public GpuResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando GPU por ID: " + id);
        Gpu gpu = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GPU no encontrada con ID: " + id));
        return mapearAResponseDTO(gpu);
    }

    public GpuResponseDTO actualizarGpu(Long id, GpuRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando GPU con ID: " + id);
        Gpu existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GPU no encontrada con ID: " + id));

        existente.setComponentId(request.getComponentId());
        existente.setVram(request.getVram());
        existente.setConsumoWatts(request.getConsumoWatts());
        existente.setFrecuencia(request.getFrecuencia());

        Gpu actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarGpu(Long id) {
        System.out.println("Ejecutando metodo: Eliminando GPU con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("GPU no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private GpuResponseDTO mapearAResponseDTO(Gpu gpu) {
        GpuResponseDTO dto = new GpuResponseDTO();
        dto.setId(gpu.getId());
        dto.setComponentId(gpu.getComponentId());
        dto.setVram(gpu.getVram());
        dto.setConsumoWatts(gpu.getConsumoWatts());
        dto.setFrecuencia(gpu.getFrecuencia());
        return dto;
    }
}
