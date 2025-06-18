package com.example.GestorInventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.example.GestorInventario.model.Equipo;

import com.example.GestorInventario.service.EquipoService;
import com.example.GestorInventario.service.EstadoService;

@WebMvcTest(EquipoController.class)
public class EquipoControllerTest {
    @MockBean
    private EquipoService equipoService;

    @MockBean
    private EstadoService estadoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mostrarTodosLosEquipos_returnsOKAndJson() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(1);
        equipo.setNombre("Tractor");
        equipo.setMarca("John Deere");
        equipo.setModelo("5055E");

        when(equipoService.mostrarTodosLosEquipos()).thenReturn(List.of(equipo));

        mockMvc.perform(get("/api/v1/equipos")) // Ajusta si tu base URL es diferente
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEquipo").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Tractor"))
                .andExpect(jsonPath("$[0].marca").value("John Deere"))
                .andExpect(jsonPath("$[0].modelo").value("5055E"));
    }

    @Test
    void obtenerEquipoPorId_returnsOK() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(1);
        equipo.setNombre("Tractor");

        when(equipoService.obtenerEquipoPorId(1)).thenReturn(equipo);

        mockMvc.perform(get("/api/v1/equipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEquipo").value(1))
                .andExpect(jsonPath("$.nombre").value("Tractor"));
    }

    @Test
    void guardarEquipo_returnsCreated() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(1);
        equipo.setNombre("Sembradora");

        when(equipoService.guardarEquipo(any(Equipo.class))).thenReturn(equipo);

        mockMvc.perform(post("/api/v1/equipos/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Sembradora\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEquipo").value(1))
                .andExpect(jsonPath("$.nombre").value("Sembradora"));
    }

    @Test
    void eliminarEquipo_returnsNoContent() throws Exception {
        doNothing().when(equipoService).eliminarEquipo(1);

        mockMvc.perform(delete("/api/v1/equipos/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    void buscarEquiposPorEstado_returnsOK() throws Exception {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(1);
        equipo.setNombre("Tractor");

        when(estadoService.buscarEquiposPorEstado("Disponible")).thenReturn(List.of(equipo));

        mockMvc.perform(get("/api/v1/equipos/estado/Disponible"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEquipo").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Tractor"));
    }

    @Test
    void modificarEquipo_returnsOK() throws Exception {
        Equipo modificado = new Equipo();
        modificado.setIdEquipo(1);
        modificado.setNombre("Modificado");

        when(equipoService.modificarEquipo(Mockito.eq(1), any(Equipo.class))).thenReturn(modificado);

        mockMvc.perform(put("/api/v1/equipos/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Modificado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Modificado"));
    }
}
