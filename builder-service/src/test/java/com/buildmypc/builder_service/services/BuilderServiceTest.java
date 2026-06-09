package com.buildmypc.builder_service.services;

import com.buildmypc.builder_service.client.*;
import com.buildmypc.builder_service.dto.PcBuildRequestDTO;
import com.buildmypc.builder_service.dto.PcBuildResponseDTO;
import com.buildmypc.builder_service.model.PcBuild;
import com.buildmypc.builder_service.repository.PcBuildRepository;
import com.buildmypc.builder_service.service.PcBuildService;
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
public class BuilderServiceTest {

    @Mock
    private PcBuildRepository pcBuildRepository;

    // Mockeamos los clientes Feign para que la validación del DTO pase sin errores
    @Mock private CpuClient cpuClient;
    @Mock private MotherboardClient motherboardClient;
    @Mock private RamClient ramClient;
    @Mock private GpuClient gpuClient;
    @Mock private StorageClient storageClient;
    @Mock private PsuClient psuClient;

    @InjectMocks
    private PcBuildService pcBuildService;

    // Declaramos nuestras variables de prueba, incluyendo la Entidad y el RequestDTO
    private PcBuild builderPrueba;
    private PcBuildRequestDTO requestPrueba;
    private List<PcBuild> builderList = new ArrayList<>();

    @BeforeEach

    public void setUp() {
        // Configuramos la Entidad que simula salir de la Base de Datos
        this.builderPrueba = new PcBuild();
        this.builderPrueba.setId(1L);
        this.builderPrueba.setCpuId(2L);
        this.builderPrueba.setGpuId(3L);
        this.builderPrueba.setMotherboardId(4L);
        this.builderPrueba.setNombreEnsamble("PC Gamer 2026");
        this.builderPrueba.setPsuId(5L);
        this.builderPrueba.setRamId(6L);
        this.builderPrueba.setStorageId(7L);

        // Configuramos el DTO de Request que simula entrar desde el Controlador
        this.requestPrueba = new PcBuildRequestDTO();
        this.requestPrueba.setCpuId(2L);
        this.requestPrueba.setGpuId(3L);
        this.requestPrueba.setMotherboardId(4L);
        this.requestPrueba.setNombreEnsamble("PC Gamer 2026");
        this.requestPrueba.setPsuId(5L);
        this.requestPrueba.setRamId(6L);
        this.requestPrueba.setStorageId(7L);

        Faker faker = new Faker(Locale.of("es","CL"));
        builderList.clear();

        for (int i=0; i <100; i++){
            PcBuild builderGenerado = new PcBuild();
            String numeroStr = faker.idNumber().valid().replace("-","");
            String ultimo = numeroStr.substring(numeroStr.length()-1);
            String restante = numeroStr.substring(0, numeroStr.length()-1);

            builderGenerado.setId((long) (i + 2));
            builderGenerado.setStorageId(1L);
            builderGenerado.setCpuId(2L);
            builderGenerado.setGpuId(3L);
            builderGenerado.setRamId(6L);
            builderGenerado.setPsuId(5L);
            builderGenerado.setMotherboardId(4L);
            builderGenerado.setNombreEnsamble(faker.name().fullName());

            builderList.add(builderGenerado);
        }
    }

    @Test
    @DisplayName("Debe listar todos los PCs armados devolviendo ResponseDTOs")
    public void shouldBeListAllPc(){
        when(pcBuildRepository.findAll()).thenReturn(builderList);

        // Usamos el método de tu service que devuelve una lista de DTOs
        List<PcBuildResponseDTO> resultado = pcBuildService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(pcBuildRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un ensamble por su ID y devolver su ResponseDTO")
    public void shouldFindPcById() {
        when(pcBuildRepository.findById(1L)).thenReturn(Optional.of(builderPrueba));

        // El método retorna el ResponseDTO mapeado
        PcBuildResponseDTO resultado = pcBuildService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("PC Gamer 2026", resultado.getNombreEnsamble());
        verify(pcBuildRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar un ensamble validando los clientes Feign")
    public void shouldSavePc() {
        // Simulamos que al guardar en el repo, nos retorna nuestra entidad con ID
        when(pcBuildRepository.save(any(PcBuild.class))).thenReturn(builderPrueba);

        // Pasamos el RequestDTO como lo pide el Service
        PcBuildResponseDTO resultado = pcBuildService.crearEnsamble(requestPrueba);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("PC Gamer 2026", resultado.getNombreEnsamble());

        // Verificamos que se haya intentado guardar en la BD
        verify(pcBuildRepository, times(1)).save(any(PcBuild.class));

        // Verificamos que OpenFeign haya validado las piezas principales
        verify(cpuClient, times(1)).obtenerCpuPorId(2L);
        verify(motherboardClient, times(1)).obtenerMotherboardPorId(4L);
    }

    @Test
    @DisplayName("Debe eliminar un ensamble correctamente")
    public void shouldDeletePc() {
        when(pcBuildRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pcBuildRepository).deleteById(1L);

        pcBuildService.eliminarEnsamble(1L);

        verify(pcBuildRepository, times(1)).existsById(1L);
        verify(pcBuildRepository, times(1)).deleteById(1L);
    }
}