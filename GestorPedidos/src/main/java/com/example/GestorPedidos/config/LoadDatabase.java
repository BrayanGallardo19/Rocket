package com.example.GestorPedidos.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.TipoRepository;

import jakarta.transaction.Transactional;

@Configuration
public class LoadDatabase {
    @Bean
    @Transactional
    public CommandLineRunner precargarTipos(TipoRepository tipoRepository) {
        return args -> {
            List<String> tipos = List.of("Online", "Presencial", "Express", "Programado");

            boolean anyAdded = false;
            for (String nombreTipo : tipos) {
                boolean existe = tipoRepository.existsByNombre(nombreTipo);
                if (!existe) {
                    Tipo tipo = new Tipo();
                    tipo.setNombre(nombreTipo);
                    tipoRepository.save(tipo);
                    anyAdded = true;
                }
            }
            if (anyAdded) {
                System.out.println("📦 Tipos precargados en la base de datos.");
            } else {
                System.out.println("📦 Ya existen datos en la base. No se cargó nada nuevo.");
            }
        };
    }
}
