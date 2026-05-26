package com.buildmypc.cpu_service.service;

import com.buildmypc.cpu_service.client.ComponentClient;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.model.Cpu;
import com.buildmypc.cpu_service.repository.CpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CpuService {

    @Autowired
    private CpuRepository repository;

    @Autowired
    private ComponentClient componentClient;

    public CpuResponseDTO crearCpu(CpuRequestDTO request) {
        System.out.println("Ejecutando metodo: Creando nueva CPU");

        // --- validacion con openfeing ---
        // Pregunta al puerto 8081 si existe el componente base. Si no, explota y no guarda.
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        Cpu cpu = new Cpu();
        cpu.setComponentId(request.getComponentId());
        cpu.setSocket(request.getSocket());
        cpu.setCores(request.getCores());
        cpu.setFrecuenciaBase(request.getFrecuenciaBase());
        // Agregados los campos faltantes
        cpu.setThreads(request.getThreads());
        cpu.setTdp(request.getTdp());

        Cpu guardado = repository.save(cpu);
        return mapearAResponseDTO(guardado);
    }

    public List<CpuResponseDTO> obtenerTodas() {
        System.out.println("Ejecutando metodo: Listando todas las CPUs");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public CpuResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando CPU por ID: " + id);
        Cpu cpu = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CPU no encontrada con ID: " + id));
        return mapearAResponseDTO(cpu);
    }

    public CpuResponseDTO actualizarCpu(Long id, CpuRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando CPU con ID: " + id);
        Cpu existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CPU no encontrada con ID: " + id));

        // --- validacion openfeing ---
        // También validamos al actualizar, por si el usuario cambia el ID del componente
        System.out.println("Validando existencia del componente general ID: " + request.getComponentId());
        componentClient.obtenerComponentePorId(request.getComponentId());

        existente.setComponentId(request.getComponentId());
        existente.setSocket(request.getSocket());
        existente.setCores(request.getCores());
        existente.setFrecuenciaBase(request.getFrecuenciaBase());
        // Agregados los campos faltantes
        existente.setThreads(request.getThreads());
        existente.setTdp(request.getTdp());

        Cpu actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarCpu(Long id) {
        System.out.println("Ejecutando metodo: Eliminando CPU con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("CPU no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private CpuResponseDTO mapearAResponseDTO(Cpu cpu) {
        CpuResponseDTO dto = new CpuResponseDTO();
        dto.setId(cpu.getId());
        dto.setComponentId(cpu.getComponentId());
        dto.setSocket(cpu.getSocket());
        dto.setCores(cpu.getCores());
        dto.setFrecuenciaBase(cpu.getFrecuenciaBase());
        // Agregados los campos faltantes
        dto.setThreads(cpu.getThreads());
        dto.setTdp(cpu.getTdp());
        return dto;
    }
}