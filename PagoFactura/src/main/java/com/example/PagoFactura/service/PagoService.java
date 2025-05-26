package com.example.PagoFactura.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.PagoFactura.Model.Pago;
import com.example.PagoFactura.repository.PagoRepository;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago RegistrarPago(Pago pago) {
        try {
            return pagoRepository.save(pago);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el pago: " + e.getMessage());
        }
        
    }

    public Pago obtenerPagoPorId(Integer id) {
        return pagoRepository.findById(id) 
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        }
    }



