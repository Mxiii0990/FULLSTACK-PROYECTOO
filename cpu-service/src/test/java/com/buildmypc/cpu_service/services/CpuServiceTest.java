package com.buildmypc.cpu_service.services;

import com.buildmypc.cpu_service.client.ComponentClient;
import com.buildmypc.cpu_service.dto.CpuRequestDTO;
import com.buildmypc.cpu_service.dto.CpuResponseDTO;
import com.buildmypc.cpu_service.model.Cpu;
import com.buildmypc.cpu_service.repository.CpuRepository;
import com.buildmypc.cpu_service.service.CpuService;
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
public class CpuServiceTest {

    @Mock
    private CpuRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private CpuService service;

    private Cpu cpuPrueba;
    private CpuRequestDTO requestPrueba;
    private List<Cpu> cpuList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.cpuPrueba = new Cpu();
        this.cpuPrueba.setId(1L);
        this.cpuPrueba.setComponentId(15L);
        this.cpuPrueba.setSocket("AM5");
        this.cpuPrueba.setCores(8);
        this.cpuPrueba.setThreads(16);
        this.cpuPrueba.setTdp(105);
        this.cpuPrueba.setFrecuenciaBase(4.5);

        this.requestPrueba = new CpuRequestDTO();
        this.requestPrueba.setComponentId(15L);
        this.requestPrueba.setSocket("AM5");
        this.requestPrueba.setCores(8);
        this.requestPrueba.setThreads(16);
        this.requestPrueba.setTdp(105);
        this.requestPrueba.setFrecuenciaBase(4.5);

        Faker faker = new Faker(Locale.of("es","CL"));
        cpuList.clear();

        for (int i = 0; i < 100; i++) {
            Cpu fakeCpu = new Cpu();
            fakeCpu.setId((long) (i + 2));
            fakeCpu.setComponentId((long) faker.number().numberBetween(1, 500));
            fakeCpu.setSocket(faker.options().option("AM4", "AM5", "LGA1700", "LGA1200"));
            fakeCpu.setCores(faker.options().option(4, 6, 8, 12, 16, 24));
            fakeCpu.setThreads(fakeCpu.getCores() * 2);
            fakeCpu.setTdp(faker.options().option(65, 105, 125, 170));
            fakeCpu.setFrecuenciaBase(faker.number().randomDouble(1, 2, 5));

            cpuList.add(fakeCpu);
        }
    }

    @Test
    @DisplayName("Debe listar todas las CPUs devolviendo ResponseDTOs")
    public void shouldBeListAllCpus(){
        when(repository.findAll()).thenReturn(cpuList);

        List<CpuResponseDTO> resultado = service.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una CPU por ID")
    public void shouldFindCpuById() {
        when(repository.findById(1L)).thenReturn(Optional.of(cpuPrueba));

        CpuResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("AM5", resultado.getSocket());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una CPU validando OpenFeign")
    public void shouldSaveCpu() {
        // Simulamos que el feign client NO explota
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Cpu.class))).thenReturn(cpuPrueba);

        CpuResponseDTO resultado = service.crearCpu(requestPrueba);

        assertNotNull(resultado);
        assertEquals("AM5", resultado.getSocket());
        verify(componentClient, times(1)).obtenerComponentePorId(15L);
        verify(repository, times(1)).save(any(Cpu.class));
    }

    @Test
    @DisplayName("Debe eliminar una CPU")
    public void shouldDeleteCpu() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarCpu(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}