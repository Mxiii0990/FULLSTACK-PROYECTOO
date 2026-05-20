package com.buildmypc.user_service.service;

import com.buildmypc.user_service.dto.UserRequestDTO;
import com.buildmypc.user_service.dto.UserResponseDTO;
import com.buildmypc.user_service.model.User;
import com.buildmypc.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserResponseDTO crearUsuario(UserRequestDTO request) {
        System.out.println("Ejecutando metodo: Registrando un nuevo usuario");
        User user = new User();
        user.setNombre(request.getNombre());
        user.setCorreo(request.getCorreo());
        user.setPassword(request.getPassword()); // En un proyecto real, aquí iría encriptada
        user.setRol(request.getRol());

        User guardado = repository.save(user);
        return mapearAResponseDTO(guardado);
    }

    public List<UserResponseDTO> obtenerTodos() {
        System.out.println("Ejecutando metodo: Listando todos los usuarios registrados");
        return repository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO obtenerPorId(Long id) {
        System.out.println("Ejecutando metodo: Buscando usuario por ID: " + id);
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapearAResponseDTO(user);
    }

    public UserResponseDTO actualizarUsuario(Long id, UserRequestDTO request) {
        System.out.println("Ejecutando metodo: Actualizando datos del usuario con ID: " + id);
        User existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existente.setNombre(request.getNombre());
        existente.setCorreo(request.getCorreo());
        existente.setPassword(request.getPassword());
        existente.setRol(request.getRol());

        User actualizado = repository.save(existente);
        return mapearAResponseDTO(actualizado);
    }

    public void eliminarUsuario(Long id) {
        System.out.println("Ejecutando metodo: Borrando usuario con ID: " + id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    private UserResponseDTO mapearAResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setCorreo(user.getCorreo());
        dto.setRol(user.getRol());
        return dto;
    }
}