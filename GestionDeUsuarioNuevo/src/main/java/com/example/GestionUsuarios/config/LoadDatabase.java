package com.example.GestionUsuarios.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.GestionUsuarios.model.User;
import com.example.GestionUsuarios.repository.UserRepository;
import com.example.GestionUsuarios.webclient.RoleClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadDatabase {
    private final UserRepository userRepository;
    private final RoleClient roleClient;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleClient roleClient) {
        return args -> {
            if (userRepository.count() == 0) {
                // Obtener roles desde microservicio de roles
                Map<String, Object> adminRole = roleClient.obtenerRolPorNombre("Administrador");
                Map<String, Object> inventarioRole = roleClient.obtenerRolPorNombre("Gestor de Inventario");
                Map<String, Object> logisticaRole = roleClient.obtenerRolPorNombre("Coordinador Logístico");
                Map<String, Object> soporteRole = roleClient.obtenerRolPorNombre("Soporte Técnico");
                Map<String, Object> clienteRole = roleClient.obtenerRolPorNombre("Cliente");

                // ADMIN
    User admin = new User();
    admin.setNombre("Administrador");
    admin.setAppaterno("Sistema");
    admin.setApmaterno("Central");
    admin.setRut("11111111-1");
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin123"));
    admin.setIdRol(getIdFromRole(adminRole));

    // GESTOR DE INVENTARIO
    User gestorInv = new User();
    gestorInv.setNombre("Gestor");
    gestorInv.setAppaterno("Inventario");
    gestorInv.setApmaterno("Demo");
    gestorInv.setRut("22222222-2");
    gestorInv.setUsername("gestorInv");
    gestorInv.setPassword(passwordEncoder.encode("gestor123"));
    gestorInv.setIdRol(getIdFromRole(inventarioRole));  

    // COORDINADOR LOGÍSTICO
    User coordLog = new User();
    coordLog.setNombre("Coordinador");
    coordLog.setAppaterno("Logístico");
    coordLog.setApmaterno("Demo");
    coordLog.setRut("33333333-3");
    coordLog.setUsername("coordLog");
    coordLog.setPassword(passwordEncoder.encode("coord123"));
    coordLog.setIdRol(getIdFromRole(logisticaRole));   

    // SOPORTE TÉCNICO
    User soporte = new User();
    soporte.setNombre("Soporte");
    soporte.setAppaterno("Técnico");
    soporte.setApmaterno("Demo");
    soporte.setRut("44444444-4");
    soporte.setUsername("soporte");
    soporte.setPassword(passwordEncoder.encode("soporte123"));
    soporte.setIdRol(getIdFromRole(soporteRole));

    // CLIENTE
    User cliente = new User();
    cliente.setNombre("Cliente");
    cliente.setAppaterno("Usuario");
    cliente.setApmaterno("Demo");
    cliente.setRut("55555555-5");
    cliente.setUsername("cliente1");
    cliente.setPassword(passwordEncoder.encode("cliente123"));
    cliente.setIdRol(getIdFromRole(clienteRole));

    userRepository.saveAll(List.of(admin, gestorInv, coordLog, soporte, cliente));
    System.out.println("Usuarios precargados con roles.");
            } else {
                System.out.println("Ya existen usuarios en la base, no se cargó nada nuevo.");
            }
        };
    }

    private Integer getIdFromRole(Map<String, Object> roleMap) {
        Object idObj = roleMap.get("idRole");
        if (idObj instanceof Integer) {
            return (Integer) idObj;
        } else if (idObj instanceof String) {
            return Integer.parseInt((String) idObj);
        } else {
            throw new IllegalArgumentException("ID de rol inválido: " + idObj);
        }
    }

}
