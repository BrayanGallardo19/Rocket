package com.example.PagoFactura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PagoFactura.Model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
