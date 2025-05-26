package com.example.PagoFactura.service;

import org.springframework.stereotype.Service;

import com.example.PagoFactura.Model.DetalleVenta;
import com.example.PagoFactura.repository.DetalleVentaRepository;

@Service

public class DetalleVentaService {
    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    public DetalleVenta obtenerDetalleVentaporId(Integer id) {
        return detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + id));
    }

    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public void eliminarDetalleVenta(Integer id) {
        if (!detalleVentaRepository.existsById(id)) {
            throw new RuntimeException("Detalle de venta no encontrada");
        }
        detalleVentaRepository.deleteById(id);
    }

}
