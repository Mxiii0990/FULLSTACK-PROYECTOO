package com.buildmypc.user_service.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
}