package com.example.GestorInventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.MarcaRepository;
import com.example.GestorInventario.repository.ModeloRepository;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(MarcaRepository marcaRepo, ModeloRepository modeloRepo,
            EquipoRepository equipoRepo) {
        return args -> {
            // si las tablas están vacías o no hay registros
            if (marcaRepo.count() == 0 && modeloRepo.count() == 0 && equipoRepo.count() == 0) {
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

                // cargar los productos completos
                // cargar equipos para John Deere 7200

                Equipo jd7200Tractor = new Equipo();
                jd7200Tractor.setNombre("Tractor John Deere 7200");
                jd7200Tractor.setPrecioVenta(850000);
                jd7200Tractor.setPrecioArriendo(3000);
                jd7200Tractor.setPatente("JD7200-A");
                jd7200Tractor.setModelo(jd7200);
                equipoRepo.save(jd7200Tractor);

                Equipo jd7200Cosechadora = new Equipo();
                jd7200Cosechadora.setNombre("Cosechadora John Deere 7200");
                jd7200Cosechadora.setPrecioVenta(1200000);
                jd7200Cosechadora.setPrecioArriendo(5000);
                jd7200Cosechadora.setPatente("JD7200-B");
                jd7200Cosechadora.setModelo(jd7200);
                equipoRepo.save(jd7200Cosechadora);

                // cargar equipos para John Deere 5055E
                Equipo jd5055Tractor = new Equipo();
                jd5055Tractor.setNombre("Tractor John Deere 5055E");
                jd5055Tractor.setPrecioVenta(650000);
                jd5055Tractor.setPrecioArriendo(2500);
                jd5055Tractor.setPatente("JD5055E-A");
                jd5055Tractor.setModelo(jd5055);
                equipoRepo.save(jd5055Tractor);

                // cargar equipos para New Holland T6050
                Equipo nhT6050Tractor = new Equipo();
                nhT6050Tractor.setNombre("Tractor New Holland T6050");
                nhT6050Tractor.setPrecioVenta(700000);
                nhT6050Tractor.setPrecioArriendo(2700);
                nhT6050Tractor.setPatente("NHT6050-A");
                nhT6050Tractor.setModelo(nhT6050);
                equipoRepo.save(nhT6050Tractor);

                // cargar equipos para Case IH Puma 185
                Equipo casePumaTractor = new Equipo();
                casePumaTractor.setNombre("Tractor Case IH Puma 185");
                casePumaTractor.setPrecioVenta(780000);
                casePumaTractor.setPrecioArriendo(2900);
                casePumaTractor.setPatente("CHPUMA185-A");
                casePumaTractor.setModelo(casePuma);
                equipoRepo.save(casePumaTractor);

                Equipo casePumaSembradora = new Equipo();
                casePumaSembradora.setNombre("Sembradora Case IH Puma 185");
                casePumaSembradora.setPrecioVenta(1100000);
                casePumaSembradora.setPrecioArriendo(4800);
                casePumaSembradora.setPatente("CHPUMA185-B");
                casePumaSembradora.setModelo(casePuma);
                equipoRepo.save(casePumaSembradora);

                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Datos ya existentes. No se cargaron nuevos datos");
            }
        };
    }
}
