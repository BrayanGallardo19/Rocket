package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.PagoRepository;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public Pago RegistrarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Optional<Pago> obtenerPagoPorId(Integer id) {
        return pagoRepository.findById(id);
    }

}
