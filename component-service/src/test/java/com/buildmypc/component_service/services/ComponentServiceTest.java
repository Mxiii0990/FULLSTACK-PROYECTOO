package com.buildmypc.component_service.services;

import com.buildmypc.component_service.dto.ComponentRequestDTO;
import com.buildmypc.component_service.dto.ComponentResponseDTO;
import com.buildmypc.component_service.model.Component;
import com.buildmypc.component_service.repository.ComponentRepository;
import com.buildmypc.component_service.service.ComponentService;
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
public class ComponentServiceTest {

    @Mock
    private ComponentRepository repository;

    @InjectMocks
    private ComponentService service;

    private Component compPrueba;
    private ComponentRequestDTO requestPrueba;
    private List<Component> compList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // Configuramos la Entidad estática para pruebas puntuales
        this.compPrueba = new Component();
        this.compPrueba.setId(1L);
        this.compPrueba.setNombre("Ryzen 5 7600X");
        this.compPrueba.setTipo("CPU");
        this.compPrueba.setMarca("AMD");
        this.compPrueba.setModelo("100-100000593WOF");
        this.compPrueba.setPrecio(250.0);
        this.compPrueba.setEstado("NUEVO");
        this.compPrueba.setDescripcion("Procesador potente para AM5");
        this.compPrueba.setFechaLanzamiento(LocalDate.of(2023, 1, 15));

        // Configuramos el DTO de Request
        this.requestPrueba = new ComponentRequestDTO();
        this.requestPrueba.setNombre("Ryzen 5 7600X");
        this.requestPrueba.setTipo("CPU");
        this.requestPrueba.setMarca("AMD");
        this.requestPrueba.setModelo("100-100000593WOF");
        this.requestPrueba.setPrecio(250.0);
        this.requestPrueba.setEstado("NUEVO");
        this.requestPrueba.setDescripcion("Procesador potente para AM5");
        this.requestPrueba.setFechaLanzamiento(LocalDate.of(2023, 1, 15));

        Faker faker = new Faker(Locale.of("es","CL"));
        compList.clear();

        for (int i = 0; i < 100; i++) {
            Component fakeComp = new Component();
            fakeComp.setId((long) (i + 2));
            fakeComp.setNombre(faker.commerce().productName());
            fakeComp.setTipo(faker.options().option("CPU", "GPU", "RAM", "MOTHERBOARD"));
            fakeComp.setMarca(faker.company().name());
            fakeComp.setModelo(faker.code().asin());
            fakeComp.setPrecio(faker.number().randomDouble(2, 50, 1500));
            fakeComp.setEstado("NUEVO");
            fakeComp.setDescripcion(faker.lorem().sentence());
            // Genera fechas aleatorias en el pasado
            fakeComp.setFechaLanzamiento(LocalDate.now().minusDays(faker.number().numberBetween(1, 1000)));

            compList.add(fakeComp);
        }
    }

    @Test
    @DisplayName("Debe listar todos los componentes devolviendo ResponseDTOs")
    public void shouldBeListAllComponents(){
        when(repository.findAll()).thenReturn(compList);

        List<ComponentResponseDTO> resultado = service.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(100, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un componente por ID")
    public void shouldFindComponentById() {
        when(repository.findById(1L)).thenReturn(Optional.of(compPrueba));

        ComponentResponseDTO resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ryzen 5 7600X", resultado.getNombre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe crear un componente correctamente")
    public void shouldSaveComponent() {
        when(repository.save(any(Component.class))).thenReturn(compPrueba);

        ComponentResponseDTO resultado = service.crearComponente(requestPrueba);

        assertNotNull(resultado);
        assertEquals("Ryzen 5 7600X", resultado.getNombre());
        verify(repository, times(1)).save(any(Component.class));
    }

    @Test
    @DisplayName("Debe eliminar un componente")
    public void shouldDeleteComponent() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminarComponente(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}