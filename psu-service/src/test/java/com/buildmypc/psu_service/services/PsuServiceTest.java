package com.buildmypc.psu_service.services;

import com.buildmypc.psu_service.client.ComponentClient;
import com.buildmypc.psu_service.dto.PsuRequestDTO;
import com.buildmypc.psu_service.dto.PsuResponseDTO;
import com.buildmypc.psu_service.model.Psu;
import com.buildmypc.psu_service.repository.PsuRepository;
import com.buildmypc.psu_service.service.PsuService;
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
public class PsuServiceTest {

    @Mock
    private PsuRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private PsuService service;

    private Psu psuPrueba;
    private PsuRequestDTO requestPrueba;
    private List<Psu> psuList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.psuPrueba = new Psu();
        this.psuPrueba.setId(1L);
        this.psuPrueba.setComponentId(33L);
        this.psuPrueba.setWatts(850);
        this.psuPrueba.setCertificacion("80 Plus Gold");
        this.psuPrueba.setModular(true);

        this.requestPrueba = new PsuRequestDTO();
        this.requestPrueba.setComponentId(33L);
        this.requestPrueba.setWatts(850);
        this.requestPrueba.setCertificacion("80 Plus Gold");
        this.requestPrueba.setModular(true);

        Faker faker = new Faker(Locale.of("es","CL"));
        psuList.clear();

        for (int i = 0; i < 100; i++) {
            Psu fakePsu = new Psu();
            fakePsu.setId((long) (i + 2));
            fakePsu.setComponentId((long) faker.number().numberBetween(1, 500));
            fakePsu.setWatts(faker.options().option(450, 550, 650, 750, 850, 1000, 1200));
            fakePsu.setCertificacion(faker.options().option("Ninguna", "80 Plus White", "80 Plus Bronze", "80 Plus Gold", "80 Plus Platinum"));
            fakePsu.setModular(faker.bool().bool());

            psuList.add(fakePsu);
        }
    }

    @Test
    @DisplayName("Debe listar todas las fuentes de poder devolviendo ResponseDTOs")
    public void shouldBeListAllPsus(){
        when(repository.findAll()).thenReturn(psuList);

        List<PsuResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una fuente de poder por ID")
    public void shouldFindPsuById() {
        when(repository.findById(1L)).thenReturn(Optional.of(psuPrueba));

        PsuResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(850, resultado.getWatts());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una fuente de poder validando OpenFeign")
    public void shouldSavePsu() {
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Psu.class))).thenReturn(psuPrueba);

        PsuResponseDTO resultado = service.crearPsu(requestPrueba);

        assertNotNull(resultado);
        assertEquals("80 Plus Gold", resultado.getCertificacion());
        verify(componentClient, times(1)).obtenerComponentePorId(33L);
        verify(repository, times(1)).save(any(Psu.class));
    }

    @Test
    @DisplayName("Debe eliminar una fuente de poder")
    public void shouldDeletePsu() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarPsu(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}