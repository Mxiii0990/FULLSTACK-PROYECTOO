package com.buildmypc.builder_service.service;

import com.buildmypc.builder_service.client.*; // Importamos todos los clientes
import com.buildmypc.builder_service.dto.PcBuildRequestDTO;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import com.buildmypc.builder_service.model.PcBuild;
import com.buildmypc.builder_service.repository.PcBuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PcBuildService {

    @Autowired
    private PcBuildRepository repository;

    // --- INYECCIÓN DE LOS CLIENTES FEIGN ---
    @Autowired private CpuClient cpuClient;
    @Autowired private MotherboardClient motherboardClient;
    @Autowired private RamClient ramClient;
    @Autowired private GpuClient gpuClient;
    @Autowired private StorageClient storageClient;
    @Autowired private PsuClient psuClient;

    public PcBuildResponseDTO crearEnsamble(PcBuildRequestDTO request) {
        System.out.println("Ejecutando metodo: Guardando un nuevo PC armado");

        // --- VALIDACIONES MULTIPLES CON OPENFEIGN ---
        System.out.println("Validando que todas las piezas existan en sus respectivos microservicios...");
        if (request.getCpuId() != null) cpuClient.obtenerCpuPorId(request.getCpuId());
        if (request.getMotherboardId() != null) motherboardClient.obtenerMotherboardPorId(request.getMotherboardId());
        if (request.getRamId() != null) ramClient.obtenerRamPorId(request.getRamId());
        if (request.getGpuId() != null) gpuClient.obtenerGpuPorId(request.getGpuId()); // Soporta PCs sin gráfica dedicada
        if (request.getStorageId() != null) storageClient.obtenerStoragePorId(request.getStorageId());
        if (request.getPsuId() != null) psuClient.obtenerPsuPorId(request.getPsuId());

        PcBuild build = new PcBuild();
        build.setNombreEnsamble(request.getNombreEnsamble());
        build.setCpuId(request.getCpuId());
        build.setMotherboardId(request.getMotherboardId());
        build.setRamId(request.getRamId());
        build.setGpuId(request.getGpuId());
        build.setStorageId(request.getStorageId());
        build.setPsuId(request.getPsuId());

        PcBuild guardado = repository.save(build);
        return mapearAResponseDTO(guardado);
    }

    public List<PcBuildResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Listando todos los PCs armados");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public PcBuildResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando un PC armado por ID: " + id);
        PcBuild build = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ensamble no encontrado con ID: " + id));
        return mapearAResponseDTO(build);
    }

    public PcBuildResponseDTO actualizarEnsamble(Long id, PcBuildRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando un PC armado con ID: " + id);
        PcBuild existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ensamble no encontrado con ID: " + id));

        // --- VALIDACIONES AL ACTUALIZAR ---
        System.out.println("Validando existencia de las nuevas piezas...");
        if (request.getCpuId() != null) cpuClient.obtenerCpuPorId(request.getCpuId());
        if (request.getMotherboardId() != null) motherboardClient.obtenerMotherboardPorId(request.getMotherboardId());
        if (request.getRamId() != null) ramClient.obtenerRamPorId(request.getRamId());
        if (request.getGpuId() != null) gpuClient.obtenerGpuPorId(request.getGpuId());
        if (request.getStorageId() != null) storageClient.obtenerStoragePorId(request.getStorageId());
        if (request.getPsuId() != null) psuClient.obtenerPsuPorId(request.getPsuId());

        existente.setNombreEnsamble(request.getNombreEnsamble());
        existente.setCpuId(request.getCpuId());
        existente.setMotherboardId(request.getMotherboardId());
        existente.setRamId(request.getRamId());
        existente.setGpuId(request.getGpuId());
        existente.setStorageId(request.getStorageId());
        existente.setPsuId(request.getPsuId());

        PcBuild actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarEnsamble(Long id) {
        System.out.println("Ejecutando metodo: Borrando ensamble con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ensamble no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    private PcBuildResponseDTO mapearAResponseDTO(PcBuild build) {
        PcBuildResponseDTO dto = new PcBuildResponseDTO();
        dto.setId(build.getId());
        dto.setNombreEnsamble(build.getNombreEnsamble());
        dto.setCpuId(build.getCpuId());
        dto.setMotherboardId(build.getMotherboardId());
        dto.setRamId(build.getRamId());
        dto.setGpuId(build.getGpuId());
        dto.setStorageId(build.getStorageId());
        dto.setPsuId(build.getPsuId());
        return dto;
    }
}