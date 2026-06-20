package com.buildmypc.gpu_service.services;

import com.buildmypc.gpu_service.client.ComponentClient;
import com.buildmypc.gpu_service.dto.GpuRequestDTO;
import com.buildmypc.gpu_service.dto.GpuResponseDTO;
import com.buildmypc.gpu_service.model.Gpu;
import com.buildmypc.gpu_service.repository.GpuRepository;
import com.buildmypc.gpu_service.service.GpuService;
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
public class GpuServiceTest {

    @Mock
    private GpuRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private GpuService service;

    private Gpu gpuPrueba;
    private GpuRequestDTO requestPrueba;
    private List<Gpu> gpuList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.gpuPrueba = new Gpu();
        this.gpuPrueba.setId(1L);
        this.gpuPrueba.setComponentId(22L);
        this.gpuPrueba.setVram(12);
        this.gpuPrueba.setConsumoWatts(320);
        this.gpuPrueba.setFrecuencia(2500);

        this.requestPrueba = new GpuRequestDTO();
        this.requestPrueba.setComponentId(22L);
        this.requestPrueba.setVram(12);
        this.requestPrueba.setConsumoWatts(320);
        this.requestPrueba.setFrecuencia(2500);

        Faker faker = new Faker(Locale.of("es","CL"));
        gpuList.clear();

        for (int i = 0; i < 100; i++) {
            Gpu fakeGpu = new Gpu();
            fakeGpu.setId((long) (i + 2));
            fakeGpu.setComponentId((long) faker.number().numberBetween(1, 500));
            fakeGpu.setVram(faker.options().option(4, 8, 12, 16, 24));
            fakeGpu.setConsumoWatts(faker.number().numberBetween(100, 450));
            fakeGpu.setFrecuencia(faker.number().numberBetween(1500, 3000));

            gpuList.add(fakeGpu);
        }
    }

    @Test
    @DisplayName("Debe listar todas las GPUs devolviendo ResponseDTOs")
    public void shouldBeListAllGpus(){
        when(repository.findAll()).thenReturn(gpuList);

        List<GpuResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una GPU por ID")
    public void shouldFindGpuById() {
        when(repository.findById(1L)).thenReturn(Optional.of(gpuPrueba));

        GpuResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(12, resultado.getVram());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una GPU validando OpenFeign")
    public void shouldSaveGpu() {
        // Simulamos que el feign client NO lanza excepción
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Gpu.class))).thenReturn(gpuPrueba);

        GpuResponseDTO resultado = service.crearGpu(requestPrueba);

        assertNotNull(resultado);
        assertEquals(12, resultado.getVram());
        verify(componentClient, times(1)).obtenerComponentePorId(22L);
        verify(repository, times(1)).save(any(Gpu.class));
    }

    @Test
    @DisplayName("Debe eliminar una GPU")
    public void shouldDeleteGpu() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarGpu(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}