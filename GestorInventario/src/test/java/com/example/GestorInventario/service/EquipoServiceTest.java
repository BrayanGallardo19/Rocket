package com.example.GestorInventario.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.webclient.MarcaClient;
import com.example.GestorInventario.webclient.ModeloClient;

@ExtendWith(MockitoExtension.class)
public class EquipoServiceTest {
    @Mock
    private EquipoRepository equipoRepository;
    @Mock
    private EstadoRepository estadoRepository;
    @Mock
    private MarcaClient marcaClient;
    @Mock
    private ModeloClient modeloClient;

    @InjectMocks
    private EquipoService equipoService;

    @Test
    void testMostrarTodosLosEquipos() {
        // crear un equipo simulado (solo IDs)
        Equipo equipoMock = new Equipo();
        equipoMock.setIdEquipo(1);
        equipoMock.setIdMarca(10);
        equipoMock.setIdModelo(20);

         // Marca mock como Map
        Map<String, Object> marcaMock = new HashMap<>();
        marcaMock.put("id", 10);
        marcaMock.put("nombre", "Marca Test");

        // Modelo mock como Map
        Map<String, Object> modeloMock = new HashMap<>();
        modeloMock.put("id", 20);
        modeloMock.put("nombre", "Modelo Test");
        // simular el comportamiento del repositorio, marca y modelo
        when(equipoRepository.findAll()).thenReturn(Arrays.asList(equipoMock));
        when(marcaClient.obtenerMarcaPorId(10)).thenReturn(marcaMock);
        when(modeloClient.obtenerModeloPorId(20)).thenReturn(modeloMock);
        // ejecutar el método a probar
        List<Equipo> resultado = equipoService.mostrarTodosLosEquipos();

        // verificaciones
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMarca()).isEqualTo("Marca Test");
        assertThat(resultado.get(0).getModelo()).isEqualTo("Modelo Test");
    }
        
}
