package com.example.PagoFactura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PagoFactura.Model.Factura;
import com.example.PagoFactura.WebClient.PedidoClient;
import com.example.PagoFactura.repository.FacturaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private PedidoClient pedidoClient; // Cliente para comunicarse con el servicio de pedidos

    // generar factura asociada a un pedido
    public Factura generarFacturaDesdePedido(Integer idPedido) {
        Map<String, Object> pedidoData = pedidoClient.obtenerPedidoPorId(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + idPedido));

        Factura factura = new Factura();
        factura.setIdPedido(idPedido);
        factura.setFechaEmision(LocalDate.now());
        factura.setEstado("Pendiente");
        return facturaRepository.save(factura);
    }

    // guardar la factura ya modificada
    public Factura actualizarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    // obtener una factura por su ID
    public Factura obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // mostrar todas las facturas
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    // marcar una factura como pagada
    public Factura marcarComoPagada(Integer idFactura) {
        Factura factura = obtenerFacturaPorId(idFactura);
        factura.setEstado("Pagada");
        return facturaRepository.save(factura);
    }

    // eliminar una factura por su ID
    public void eliminarFactura(Integer id) {
        if (!facturaRepository.existsById(id)) {
            throw new RuntimeException("Factura no encontrada");
        }
        facturaRepository.deleteById(id);
    }

}
