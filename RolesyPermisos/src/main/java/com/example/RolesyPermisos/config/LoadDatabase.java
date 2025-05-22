package com.example.RolesyPermisos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.RolesyPermisos.repository.RoleRepository;
import com.example.RolesyPermisos.model.Role;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo) {
        return args -> {
            // si las tablas están vacías o no hay registros    
            if (roleRepo.count() == 0) {
                Role Administrador = new Role();
                Administrador.setNombre("Administrador");
                roleRepo.save(Administrador);

                Role Cliente = new Role();
                Cliente.setNombre("Cliente");
                roleRepo.save(Cliente);

                Role Inventario = new Role();
                Inventario.setNombre("Inventario");
                roleRepo.save(Inventario);

                Role Logistico = new Role();
                Logistico.setNombre("Logistico");
                roleRepo.save(Logistico);

                Role Tecnico = new Role();
                Tecnico.setNombre("Tecnico");
                roleRepo.save(Tecnico);

                  System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Datos ya existentes. No se cargaron nuevos datos");
            } 
        };
    } 
}
