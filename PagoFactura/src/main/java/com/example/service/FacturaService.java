package com.example.service;

import java.lang.foreign.Linker.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.Factura;
import com.example.repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    public Factura crearFactura(Factura factura) {
        return facturaRepository.save(factura);
    
    }
    public Option<Factura> obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id);
    }

}
