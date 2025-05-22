package com.example.GestorLogisticayPedidos.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestorLogisticayPedidos.model.DetalleVenta;
import com.example.GestorLogisticayPedidos.model.Venta;
import com.example.GestorLogisticayPedidos.repository.VentaRepository;
import com.example.GestorLogisticayPedidos.webclient.EquipoClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EquipoClient equipoClient;
    

    // guardar venta con validacion de equipos
    @Transactional // para que no se guarde si hay error
    public Venta guardarVenta(Venta venta) {
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            Integer idEquipo = detalle.getIdEquipoModelo(); // obtenemos el id del equipo

            // validar si el equipo existe en el microservicio de equipo
            Map<String, Object> equipoData = equipoClient.getEquipoById(idEquipo);

            if (equipoData == null || equipoData.isEmpty()) {
                throw new RuntimeException("Equipo con ID " + idEquipo + " no encontrado en microservicio Equipo");
            }
            detalle.setVenta(venta); // asignar la venta al detalle
        }

        return ventaRepository.save(venta);
    }

    public Optional<Venta> buscarVentaPorId(Integer id) {
        return ventaRepository.findById(id);
    }


    
    // mostrar todas las ventas
    public List<Venta> mostrarTodasLasVentas() {
        return ventaRepository.findAll();
    }

    // buscar ventas por id de usuario
    public List<Venta> buscarVentasPorIdUsuario(Integer idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    // eliminar venta por id
    public void eliminarVentaPorId(Integer id) {
        ventaRepository.deleteById(id);
    }
}
