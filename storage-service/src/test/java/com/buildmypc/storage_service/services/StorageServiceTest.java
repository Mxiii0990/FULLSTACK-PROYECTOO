package com.buildmypc.storage_service.services;

import com.buildmypc.storage_service.client.ComponentClient;
import com.buildmypc.storage_service.dto.StorageRequestDTO;
import com.buildmypc.storage_service.dto.StorageResponseDTO;
import com.buildmypc.storage_service.model.Storage;
import com.buildmypc.storage_service.repository.StorageRepository;
import com.buildmypc.storage_service.service.StorageService;
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
public class StorageServiceTest {

    @Mock
    private StorageRepository repository;

    @Mock
    private ComponentClient componentClient;

    @InjectMocks
    private StorageService service;

    private Storage storagePrueba;
    private StorageRequestDTO requestPrueba;
    private List<Storage> storageList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.storagePrueba = new Storage();
        this.storagePrueba.setId(1L);
        this.storagePrueba.setComponentId(105L);
        this.storagePrueba.setTipo("SSD NVMe");
        this.storagePrueba.setCapacidadGb(1000);
        this.storagePrueba.setVelocidadLectura(3500);

        this.requestPrueba = new StorageRequestDTO();
        this.requestPrueba.setComponentId(105L);
        this.requestPrueba.setTipo("SSD NVMe");
        this.requestPrueba.setCapacidadGb(1000);
        this.requestPrueba.setVelocidadLectura(3500);

        Faker faker = new Faker(Locale.of("es","CL"));
        storageList.clear();

        for (int i = 0; i < 100; i++) {
            Storage fakeStorage = new Storage();
            fakeStorage.setId((long) (i + 2));
            fakeStorage.setComponentId((long) faker.number().numberBetween(1, 500));
            fakeStorage.setTipo(faker.options().option("HDD SATA", "SSD SATA", "SSD NVMe M.2", "SSD NVMe Gen4"));
            fakeStorage.setCapacidadGb(faker.options().option(250, 500, 1000, 2000, 4000));
            fakeStorage.setVelocidadLectura(faker.number().numberBetween(500, 7500));

            storageList.add(fakeStorage);
        }
    }

    @Test
    @DisplayName("Debe listar todas las unidades de almacenamiento devolviendo ResponseDTOs")
    public void shouldBeListAllStorages(){
        when(repository.findAll()).thenReturn(storageList);

        List<StorageResponseDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar una unidad de almacenamiento por ID")
    public void shouldFindStorageById() {
        when(repository.findById(1L)).thenReturn(Optional.of(storagePrueba));

        StorageResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("SSD NVMe", resultado.getTipo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear una unidad de almacenamiento validando OpenFeign")
    public void shouldSaveStorage() {
        doNothing().when(componentClient).obtenerComponentePorId(anyLong());
        when(repository.save(any(Storage.class))).thenReturn(storagePrueba);

        StorageResponseDTO resultado = service.crearStorage(requestPrueba);

        assertNotNull(resultado);
        assertEquals(1000, resultado.getCapacidadGb());
        verify(componentClient, times(1)).obtenerComponentePorId(105L);
        verify(repository, times(1)).save(any(Storage.class));
    }

    @Test
    @DisplayName("Debe eliminar una unidad de almacenamiento")
    public void shouldDeleteStorage() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarStorage(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}