package com.buildmypc.ram.service.controller;
import com.buildmypc.ram.service.dto.RamRequestDTO;
import com.buildmypc.ram.service.dto.RamResponseDTO;
import com.buildmypc.ram.service.service.RamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rams")
public class RamController {
    @Autowired
    private RamService service;

    @GetMapping
    public ResponseEntity<List<RamResponseDTO>> listarRams() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<RamResponseDTO> crearRam(@Valid @RequestBody RamRequestDTO request) {
        RamResponseDTO nuevaRam = service.crearRam(request);
        return new ResponseEntity<>(nuevaRam, HttpStatus.CREATED);
    }
}
