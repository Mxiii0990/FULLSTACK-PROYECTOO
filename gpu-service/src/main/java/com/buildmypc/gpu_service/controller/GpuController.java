package com.buildmypc.gpu_service.controller;
import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.service.GpuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpus")
public class GpuController {

    @Autowired
    private GpuService service;

    @GetMapping
    public ResponseEntity<List<GpuResponseDTO>> listarGpus() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<GpuResponseDTO> crearGpu(@Valid @RequestBody GpuRequestDTO request) {
        GpuResponseDTO nuevaGpu = service.crearGpu(request);
        return new ResponseEntity<>(nuevaGpu, HttpStatus.CREATED);
    }
}
