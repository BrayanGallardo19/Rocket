package com.example.GestorInventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.InventarioRepository;
import com.example.GestorInventario.repository.MarcaRepository;
import com.example.GestorInventario.repository.ModeloRepository;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(MarcaRepository marcaRepo, ModeloRepository modeloRepo, InventarioRepository inventarioRepo) {
        return args -> {
            // si las tablas están vacías o no hay registros
            if (marcaRepo.count() == 0 && modeloRepo.count() == 0 && inventarioRepo.count() == 0) {
                // cargar las marcas
                Marca johnDeere = new Marca();
                johnDeere.setNombreMarca("John Deere");
                marcaRepo.save(johnDeere);

                Marca newHolland = new Marca();
                newHolland.setNombreMarca("New Holland");
                marcaRepo.save(newHolland);

                Marca caseIH = new Marca();
                caseIH.setNombreMarca("Case IH");
                marcaRepo.save(caseIH);

                // cargar los modelos
                Modelo jd7200 = new Modelo();
                jd7200.setNombreModelo("7200");
                jd7200.setMarca(johnDeere);
                modeloRepo.save(jd7200);

                Modelo jd5055 = new Modelo();
                jd5055.setNombreModelo("5055E");
                jd5055.setMarca(johnDeere);
                modeloRepo.save(jd5055);

                Modelo nhT6050 = new Modelo();
                nhT6050.setNombreModelo("T6050");
                nhT6050.setMarca(newHolland);
                modeloRepo.save(nhT6050);

                Modelo casePuma = new Modelo();
                casePuma.setNombreModelo("Puma 185");
                casePuma.setMarca(caseIH);
                modeloRepo.save(casePuma);
                
                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Datos ya existentes. No se cargaron nuevos datos");
            }
        };
    }
}
