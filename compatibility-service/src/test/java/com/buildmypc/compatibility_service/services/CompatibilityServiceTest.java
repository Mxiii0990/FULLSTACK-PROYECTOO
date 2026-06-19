package com.buildmypc.compatibility_service.services;
import com.buildmypc.compatibility_service.client.BuildClient;
import com.buildmypc.compatibility_service.dto.ValidacionRequestDTO;
import com.buildmypc.compatibility_service.dto.ValidacionResponseDTO;
import com.buildmypc.compatibility_service.model.ValidacionCompatibilidad;
import com.buildmypc.compatibility_service.repository.ValidacionRepository;
import com.buildmypc.compatibility_service.service.ValidacionService;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompatibilityServiceTest {

    @Mock
    private ValidacionRepository repository;

    // Mockeamos el cliente Feign para que la validación del DTO hacia el Builder pase sin errores
    @Mock
    private BuildClient buildClient;

    @InjectMocks
    private ValidacionService service;

    // Declaramos nuestras variables de prueba, incluyendo la Entidad y el RequestDTO
    private ValidacionCompatibilidad validacionPrueba;
    private ValidacionRequestDTO requestPrueba;
    private List<ValidacionCompatibilidad> validacionList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // Configuramos la Entidad que simula salir de la Base de Datos
        this.validacionPrueba = new ValidacionCompatibilidad();
        this.validacionPrueba.setId(1L);
        this.validacionPrueba.setBuildId(15L); // Simulamos que valida el ensamble ID 15
        this.validacionPrueba.setCompatible(true);
        this.validacionPrueba.setConsumoEstimadoWatts(450); // Número entero
        this.validacionPrueba.setMargenFuente(150);         // Número entero
        this.validacionPrueba.setObservaciones("Consumo óptimo para arquitectura 2026");
        this.validacionPrueba.setFechaValidacion(LocalDate.now());

        // Configuramos el DTO de Request que simula entrar desde el Controlador
        this.requestPrueba = new ValidacionRequestDTO();
        this.requestPrueba.setBuildId(15L);
        this.requestPrueba.setCompatible(true);
        this.requestPrueba.setConsumoEstimadoWatts(450); // Número entero
        this.requestPrueba.setMargenFuente(150);         // Número entero
        this.requestPrueba.setObservaciones("Consumo óptimo para arquitectura 2026");
        this.requestPrueba.setFechaValidacion(LocalDate.now());

        Faker faker = new Faker(Locale.of("es","CL"));
        validacionList.clear();

        for (int i = 0; i < 100; i++) {
            ValidacionCompatibilidad fakeVal = new ValidacionCompatibilidad();
            fakeVal.setId((long) (i + 2));
            fakeVal.setBuildId((long) faker.number().numberBetween(1, 500));
            fakeVal.setCompatible(faker.bool().bool());

            // Generamos números enteros aleatorios entre un rango lógico
            fakeVal.setConsumoEstimadoWatts(faker.number().numberBetween(200, 800));
            fakeVal.setMargenFuente(faker.number().numberBetween(50, 300));

            fakeVal.setObservaciones(faker.lorem().sentence());
            fakeVal.setFechaValidacion(LocalDate.now());

            validacionList.add(fakeVal);
        }
    }

    @Test
    @DisplayName("Debe listar todas las validaciones devolviendo ResponseDTOs")
    public void shouldBeListAllValidaciones(){
        when(repository.findAll()).thenReturn(validacionList);

        // Usamos el método del service que devuelve la lista de DTOs
        List<ValidacionResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una validación por su ID y devolver su ResponseDTO")
    public void shouldFindValidacionById() {
        when(repository.findById(1L)).thenReturn(Optional.of(validacionPrueba));

        // El método retorna el ResponseDTO mapeado
        ValidacionResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(15L, resultado.getBuildId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar una validación verificando el cliente Feign")
    public void shouldSaveValidacion() {
        // Simulamos que al guardar en el repo, nos retorna nuestra entidad con ID
        when(repository.save(any(ValidacionCompatibilidad.class))).thenReturn(validacionPrueba);

        // Simulamos que OpenFeign NO tira error al buscar el ensamble
        doNothing().when(buildClient).obtenerEnsamblePorId(anyLong());

        // Pasamos el RequestDTO como lo pide el Service
        ValidacionResponseDTO resultado = service.crearValidacion(requestPrueba);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(15L, resultado.getBuildId());

        // Verificamos que se haya intentado guardar en la BD
        verify(repository, times(1)).save(any(ValidacionCompatibilidad.class));

        // Verificamos que OpenFeign haya ido a consultar al builder-service
        verify(buildClient, times(1)).obtenerEnsamblePorId(15L);
    }

    @Test
    @DisplayName("Debe eliminar una validación correctamente")
    public void shouldDeleteValidacion() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarValidacion(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}