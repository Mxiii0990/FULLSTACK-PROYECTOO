package com.buildmypc.motherboard_service.controller;
import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.service.MotherboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motherboards")
public class MotherboardController {
    @Autowired
    private MotherboardService service;

    @GetMapping
    public ResponseEntity<List<MotherboardResponseDTO>> listarMotherboards() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<MotherboardResponseDTO> crearMotherboard(@Valid @RequestBody MotherboardRequestDTO request) {
        MotherboardResponseDTO nuevaMotherboard = service.crearMotherboard(request);
        return new ResponseEntity<>(nuevaMotherboard, HttpStatus.CREATED);
    }
}
