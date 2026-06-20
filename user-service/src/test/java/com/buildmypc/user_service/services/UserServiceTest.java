package com.buildmypc.user_service.services;

import com.buildmypc.user_service.dto.UserRequestDTO;
import com.buildmypc.user_service.dto.UserResponseDTO;
import com.buildmypc.user_service.model.User;
import com.buildmypc.user_service.repository.UserRepository;
import com.buildmypc.user_service.service.UserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User userPrueba;
    private UserRequestDTO requestPrueba;
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.userPrueba = new User();
        this.userPrueba.setId(1L);
        this.userPrueba.setNombre("Martina Silva");
        this.userPrueba.setCorreo("martina@buildmypc.cl");
        this.userPrueba.setPassword("secreta123");
        this.userPrueba.setRol("CLIENTE");

        this.requestPrueba = new UserRequestDTO();
        this.requestPrueba.setNombre("Martina Silva");
        this.requestPrueba.setCorreo("martina@buildmypc.cl");
        this.requestPrueba.setPassword("secreta123");
        this.requestPrueba.setRol("CLIENTE");

        Faker faker = new Faker(Locale.of("es","CL"));
        userList.clear();

        for (int i = 0; i < 100; i++) {
            User fakeUser = new User();
            fakeUser.setId((long) (i + 2));
            fakeUser.setNombre(faker.name().fullName());
            fakeUser.setCorreo(faker.internet().emailAddress());
            fakeUser.setPassword(faker.internet().password(8, 16));
            fakeUser.setRol(faker.options().option("CLIENTE", "TECNICO", "ADMIN"));

            userList.add(fakeUser);
        }
    }

    @Test
    @DisplayName("Debe listar todos los usuarios devolviendo ResponseDTOs")
    public void shouldBeListAllUsers(){
        when(repository.findAll()).thenReturn(userList);

        List<UserResponseDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un usuario por ID")
    public void shouldFindUserById() {
        when(repository.findById(1L)).thenReturn(Optional.of(userPrueba));

        UserResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Martina Silva", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear un usuario correctamente")
    public void shouldSaveUser() {
        when(repository.save(any(User.class))).thenReturn(userPrueba);

        UserResponseDTO resultado = service.crearUsuario(requestPrueba);

        assertNotNull(resultado);
        assertEquals("martina@buildmypc.cl", resultado.getCorreo());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Debe eliminar un usuario")
    public void shouldDeleteUser() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarUsuario(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}