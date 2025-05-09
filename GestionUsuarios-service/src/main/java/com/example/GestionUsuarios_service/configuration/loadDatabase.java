package com.example.GestionUsuarios_service.configuration;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.GestionUsuarios_service.model.Usuario;
import com.example.GestionUsuarios_service.repository.UsuarioRepository;
@Configuration
public class loadDatabase {
    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepo){
        return args -> {
            //si las tablas están vacias - no hay registros en las tablas
            if(usuarioRepo.count() == 0){
                //cargar usuarios por defecto o iniciales
                    usuarioRepo.save(new Usuario(null,"98.765.432-1", "Ana", "López", "Martínez", "ana.lopez@example.com", "+56998765432", "anita", "contrasena123", "1985-08-22"));
                    usuarioRepo.save(new Usuario(null,"11.223.344-5", "Carlos", "Gómez", "Sánchez", "carlos.gomez@example.com", "+56987654321", "carlitos", "seguro123", "1992-11-03"));
                    usuarioRepo.save(new Usuario(null,"22.334.455-6", "Marta", "Ramírez", "Vega", "marta.ramirez@example.com", "+56987654322", "marta_123", "12345678", "1990-12-15"
                    ));
                    System.out.println("Datos iniciales creados");
            }
            else{
                System.out.println("Datos ya existentes. No se cargaron nuevos datos");
            }
        };
    }
}
