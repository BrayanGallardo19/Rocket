package com.example.GestorMarcaYModelo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GestorMarcaYModelo.model.Marca;
import com.example.GestorMarcaYModelo.model.Modelo;
import com.example.GestorMarcaYModelo.repository.MarcaRepository;
import com.example.GestorMarcaYModelo.repository.ModeloRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(MarcaRepository marcaRepo, ModeloRepository modeloRepo) {
        return args -> {
            if (marcaRepo.count() == 0 && modeloRepo.count() == 0) {

                // Crear marcas
                Marca johnDeere = new Marca();
                johnDeere.setNombreMarca("John Deere");
                marcaRepo.save(johnDeere);

                Marca newHolland = new Marca();
                newHolland.setNombreMarca("New Holland");
                marcaRepo.save(newHolland);

                Marca caseIH = new Marca();
                caseIH.setNombreMarca("Case IH");
                marcaRepo.save(caseIH);

                // Crear modelos para las marcas
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

                System.out.println("🚜 Marcas y modelos precargados correctamente");
            } else {
                System.out.println("📦 Ya existen datos en la base. No se cargó nada nuevo.");
            }
        };
    }
   }