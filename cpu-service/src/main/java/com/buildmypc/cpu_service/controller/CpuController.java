package com.buildmypc.cpu_service.controller;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.service.CpuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cpus")
public class CpuController {
    @Autowired
    private CpuService service;

    @GetMapping
    public ResponseEntity<List<CpuResponseDTO>> listarCpus() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<CpuResponseDTO> crearCpu(@Valid @RequestBody CpuRequestDTO request) {
        CpuResponseDTO nuevaCpu = service.crearCpu(request);
        return new ResponseEntity<>(nuevaCpu, HttpStatus.CREATED);
    }
}
