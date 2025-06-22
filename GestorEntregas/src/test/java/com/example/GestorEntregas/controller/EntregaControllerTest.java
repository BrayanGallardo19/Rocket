package com.example.GestorEntregas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.GestorEntregas.model.Entrega;
import com.example.GestorEntregas.service.AutorizacionService;
import com.example.GestorEntregas.service.EntregaService;
import com.example.GestorEntregas.webclient.EstadoClient;
import com.example.GestorEntregas.webclient.PedidoClient;

@WebMvcTest(EntregaController.class)
public class EntregaControllerTest {
    @MockBean
    private EntregaService entregaService;
    @MockBean
    private AutorizacionService autorizacionService;
    @MockBean
    private EstadoClient estadoClient;
    @MockBean
    private PedidoClient pedidoClient;

    @Autowired
    private MockMvc mockMvc;


    // Utilizamos ObjectMapper para convertir objetos a JSON
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearEntrega_ok() throws Exception {
        // crear objeto prueba sin id
        Entrega entregaMock = new Entrega();
        entregaMock.setComentario("Entrega urgente");
        entregaMock.setFechaProceso(LocalDate.now());
        entregaMock.setFechaEntrega(LocalDate.now().plusDays(3));
        entregaMock.setIdPedido(123);
        entregaMock.setDireccionEntrega("Calle Falsa 123");
        entregaMock.setComuna("Providencia");
        entregaMock.setCiudad("Santiago");
        entregaMock.setIdCoordinadorLogistico(45);
        entregaMock.setEstado("Pendiente");

        // mockear autorizacion el usuario tiene el rol correcto (3)
        when(autorizacionService.validarRol(4, 3))
                .thenReturn(ResponseEntity.ok().build());

        // crear el objeto
        Entrega entregaCreada = new Entrega();
        entregaCreada.setIdEntrega(1);
        entregaCreada.setComentario(entregaMock.getComentario());
        entregaCreada.setFechaProceso(entregaMock.getFechaProceso());
        entregaCreada.setFechaEntrega(entregaMock.getFechaEntrega());
        entregaCreada.setIdPedido(entregaMock.getIdPedido());
        entregaCreada.setDireccionEntrega(entregaMock.getDireccionEntrega());
        entregaCreada.setComuna(entregaMock.getComuna());
        entregaCreada.setCiudad(entregaMock.getCiudad());
        entregaCreada.setIdCoordinadorLogistico(entregaMock.getIdCoordinadorLogistico());
        entregaCreada.setEstado(entregaMock.getEstado());

        // mockear el metodo
        when(entregaService.crearEntrega(any(Entrega.class))).thenReturn(entregaCreada);

        // simular la petición POST
        mockMvc.perform(post("/api/v1/entregas/crear")
                        .header("X-User-Id", 4)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entregaMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEntrega").value(1))
                .andExpect(jsonPath("$.comentario").value("Entrega urgente"))
                .andExpect(jsonPath("$.direccionEntrega").value("Calle Falsa 123"))
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }

    @Test
    void obtenerTodasLasEntregas_ok() throws Exception {
        Entrega entregaMock = new Entrega();
        entregaMock.setIdEntrega(1);

        when(autorizacionService.validarRol(4, 3))
                .thenReturn(ResponseEntity.ok().build());
        when(entregaService.obtenerTodasLasEntregas())
                .thenReturn(List.of(entregaMock));

        mockMvc.perform(get("/api/v1/entregas")
                .header("X-User-Id", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEntrega").value(1));
    }

    @Test
    void actualizarEstadoEntrega_ok() throws Exception {
        Entrega entregaActualizada = new Entrega();
        entregaActualizada.setIdEntrega(1);

        when(autorizacionService.validarRol(4, 3))
                .thenReturn(ResponseEntity.ok().build());
        when(entregaService.actualizarEstadoEntrega(eq(1), eq("EN PROCESO")))
                .thenReturn(entregaActualizada);

        String bodyJson = "{\"nuevoEstado\": \"EN PROCESO\"}";

        mockMvc.perform(put("/api/v1/entregas/actualizar/1")
                .header("X-User-Id", 4)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntrega").value(1));
    }

    @Test
    void eliminarEntrega_ok() throws Exception {
        when(autorizacionService.validarRol(4, 3))
                .thenReturn(ResponseEntity.ok().build());

        doNothing().when(entregaService).eliminarEntrega(1);

        mockMvc.perform(delete("/api/v1/entregas/eliminar/1")
                .header("X-User-Id", 4))
                .andExpect(status().isNoContent());
    }
}
