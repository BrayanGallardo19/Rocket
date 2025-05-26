package com.example.PagoFactura.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PagoFactura.Model.Factura;
import com.example.PagoFactura.repository.FacturaRepository;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }
    
    
    public Factura crearFactura(Factura factura) {
        return facturaRepository.save(factura);
            
    
    }
    public Factura obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

}
