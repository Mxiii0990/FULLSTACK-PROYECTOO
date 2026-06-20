package com.buildmypc.motherboard_service.services;

import com.buildmypc.motherboard_service.client.ComponentClient;
import com.buildmypc.motherboard_service.dto.MotherboardRequestDTO;
import com.buildmypc.motherboard_service.dto.MotherboardResponseDTO;
import com.buildmypc.motherboard_service.model.Motherboard;
import com.buildmypc.motherboard_service.repository.MotherboardRepository;
import com.buildmypc.motherboard_service.service.MotherboardService;
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
public class MotherboardServiceTest {

    @Mock
    private MotherboardRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private MotherboardService service;

    private Motherboard mbPrueba;
    private MotherboardRequestDTO requestPrueba;
    private List<Motherboard> mbList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.mbPrueba = new Motherboard();
        this.mbPrueba.setId(1L);
        this.mbPrueba.setComponentId(18L);
        this.mbPrueba.setSocket("AM5");
        this.mbPrueba.setTipoRam("DDR5");
        this.mbPrueba.setFormato("ATX");

        this.requestPrueba = new MotherboardRequestDTO();
        this.requestPrueba.setComponentId(18L);
        this.requestPrueba.setSocket("AM5");
        this.requestPrueba.setTipoRam("DDR5");
        this.requestPrueba.setFormato("ATX");

        Faker faker = new Faker(Locale.of("es","CL"));
        mbList.clear();

        for (int i = 0; i < 100; i++) {
            Motherboard fakeMb = new Motherboard();
            fakeMb.setId((long) (i + 2));
            fakeMb.setComponentId((long) faker.number().numberBetween(1, 500));
            fakeMb.setSocket(faker.options().option("AM4", "AM5", "LGA1700", "LGA1200"));
            fakeMb.setTipoRam(faker.options().option("DDR4", "DDR5"));
            fakeMb.setFormato(faker.options().option("ATX", "Micro-ATX", "Mini-ITX"));

            mbList.add(fakeMb);
        }
    }

    @Test
    @DisplayName("Debe listar todas las placas madres devolviendo ResponseDTOs")
    public void shouldBeListAllMotherboards(){
        when(repository.findAll()).thenReturn(mbList);

        List<MotherboardResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una placa madre por ID")
    public void shouldFindMotherboardById() {
        when(repository.findById(1L)).thenReturn(Optional.of(mbPrueba));

        MotherboardResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("AM5", resultado.getSocket());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una placa madre validando OpenFeign")
    public void shouldSaveMotherboard() {
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Motherboard.class))).thenReturn(mbPrueba);

        MotherboardResponseDTO resultado = service.crearMotherboard(requestPrueba);

        assertNotNull(resultado);
        assertEquals("ATX", resultado.getFormato());
        verify(componentClient, times(1)).obtenerComponentePorId(18L);
        verify(repository, times(1)).save(any(Motherboard.class));
    }

    @Test
    @DisplayName("Debe eliminar una placa madre")
    public void shouldDeleteMotherboard() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarMotherboard(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}