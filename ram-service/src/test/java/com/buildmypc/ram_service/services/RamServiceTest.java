package com.buildmypc.ram_service.services;

import com.buildmypc.ram_service.client.ComponentClient;
import com.buildmypc.ram_service.dto.RamRequestDTO;
import com.buildmypc.ram_service.dto.RamResponseDTO;
import com.buildmypc.ram_service.model.Ram;
import com.buildmypc.ram_service.repository.RamRepository;
import com.buildmypc.ram_service.service.RamService;
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
public class RamServiceTest {

    @Mock
    private RamRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private RamService service;

    private Ram ramPrueba;
    private RamRequestDTO requestPrueba;
    private List<Ram> ramList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.ramPrueba = new Ram();
        this.ramPrueba.setId(1L);
        this.ramPrueba.setComponentId(10L);
        this.ramPrueba.setTipo("DDR5");
        this.ramPrueba.setCapacidad(32);
        this.ramPrueba.setFrecuencia(5600);

        this.requestPrueba = new RamRequestDTO();
        this.requestPrueba.setComponentId(10L);
        this.requestPrueba.setTipo("DDR5");
        this.requestPrueba.setCapacidad(32);
        this.requestPrueba.setFrecuencia(5600);

        Faker faker = new Faker(Locale.of("es","CL"));
        ramList.clear();

        for (int i = 0; i < 100; i++) {
            Ram fakeRam = new Ram();
            fakeRam.setId((long) (i + 2));
            fakeRam.setComponentId((long) faker.number().numberBetween(1, 500));
            fakeRam.setTipo(faker.options().option("DDR3", "DDR4", "DDR5"));
            fakeRam.setCapacidad(faker.options().option(8, 16, 32, 64));
            fakeRam.setFrecuencia(faker.options().option(2666, 3200, 3600, 4800, 5600, 6000));

            ramList.add(fakeRam);
        }
    }

    @Test
    @DisplayName("Debe listar todas las memorias RAM devolviendo ResponseDTOs")
    public void shouldBeListAllRams(){
        when(repository.findAll()).thenReturn(ramList);

        List<RamResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una memoria RAM por ID")
    public void shouldFindRamById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ramPrueba));

        RamResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(32, resultado.getCapacidad());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una memoria RAM validando OpenFeign")
    public void shouldSaveRam() {
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Ram.class))).thenReturn(ramPrueba);

        RamResponseDTO resultado = service.crearRam(requestPrueba);

        assertNotNull(resultado);
        assertEquals("DDR5", resultado.getTipo());
        verify(componentClient, times(1)).obtenerComponentePorId(10L);
        verify(repository, times(1)).save(any(Ram.class));
    }

    @Test
    @DisplayName("Debe eliminar una memoria RAM")
    public void shouldDeleteRam() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarRam(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}