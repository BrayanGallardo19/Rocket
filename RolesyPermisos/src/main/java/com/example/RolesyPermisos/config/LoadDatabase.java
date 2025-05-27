package com.example.RolesyPermisos.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.RolesyPermisos.repository.PermisoRepository;
import com.example.RolesyPermisos.repository.RoleRepository;
import com.example.RolesyPermisos.model.Permiso;
import com.example.RolesyPermisos.model.Role;

@Configuration
public class LoadDatabase {
    
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo, PermisoRepository permisoRepo) {
        return args -> {
            if (roleRepo.count() == 0 && permisoRepo.count() == 0) {
                
                //borrar datos existentes para empezar desde cero
                roleRepo.deleteAll();
                permisoRepo.deleteAll();

                // ===== CREAR PERMISOS =====
                
                // Permisos de Administrador
                Permiso gestionarUsuarios = crearPermiso("Gestionar Usuarios", "Crear, editar y eliminar usuarios del sistema");
                Permiso configurarPermisos = crearPermiso("Configurar Permisos", "Asignar y modificar permisos de roles");
                Permiso monitorizacionSistema = crearPermiso("Monitorización del Sistema", "Supervisar el estado general del sistema");
                Permiso respaldarRestaurarDatos = crearPermiso("Respaldar y Restaurar Datos", "Gestionar copias de seguridad y restauración");
                
                // Permisos de Gestor de Inventario
                Permiso administrarEquipos = crearPermiso("Administrar Equipos", "Gestionar catálogo de equipos disponibles");
                Permiso controlStock = crearPermiso("Control de Stock", "Monitorear y controlar inventario de equipos");
                Permiso gestionProveedores = crearPermiso("Gestión de Proveedores", "Administrar información de proveedores");
                Permiso generacionReportes = crearPermiso("Generación de Reportes", "Crear reportes de inventario y ventas");
                
                // Permisos de Coordinador Logístico
                Permiso gestionPedidos = crearPermiso("Gestión de Pedidos", "Procesar y administrar pedidos de clientes");
                Permiso organizacionEntregas = crearPermiso("Organización de Entregas", "Coordinar entregas y distribución");
                Permiso planificacionMantenimiento = crearPermiso("Planificación de Mantenimiento", "Programar mantenimiento de equipos");
                Permiso controlDevoluciones = crearPermiso("Control de Devoluciones", "Gestionar devoluciones de equipos");
                
                // Permisos de Soporte Técnico
                Permiso registroIncidencias = crearPermiso("Registro de Incidencias", "Documentar problemas técnicos reportados");
                Permiso asignacionTecnicos = crearPermiso("Asignación de Técnicos", "Asignar técnicos a casos específicos");
                Permiso monitoreoSistema = crearPermiso("Monitoreo del Sistema", "Supervisar funcionamiento técnico del sistema");
                
                // Permisos de Cliente
                Permiso registroClientes = crearPermiso("Registro de Clientes", "Registrarse como cliente en el sistema");
                Permiso busquedaEquipos = crearPermiso("Búsqueda de Equipos", "Buscar equipos en el catálogo");
                Permiso solicitudArriendoCompra = crearPermiso("Solicitud de Arriendo/Compra", "Realizar solicitudes de arriendo o compra");
                Permiso seguimientoPedidos = crearPermiso("Seguimiento de Pedidos", "Consultar estado de pedidos realizados");
                Permiso gestionPerfil = crearPermiso("Gestión de Perfil", "Administrar información personal del perfil");
                Permiso solicitudSoporteTecnico = crearPermiso("Solicitud de Soporte Técnico", "Solicitar asistencia técnica");
                Permiso dejarResenasCalificaciones = crearPermiso("Dejar Reseñas y Calificaciones", "Evaluar productos y servicios");
                
                // Guardar todos los permisos
                List<Permiso> todosLosPermisos = Arrays.asList(
                    gestionarUsuarios, configurarPermisos, monitorizacionSistema, respaldarRestaurarDatos,
                    administrarEquipos, controlStock, gestionProveedores, generacionReportes,
                    gestionPedidos, organizacionEntregas, planificacionMantenimiento, controlDevoluciones,
                    registroIncidencias, asignacionTecnicos, monitoreoSistema,
                    registroClientes, busquedaEquipos, solicitudArriendoCompra, seguimientoPedidos,
                    gestionPerfil, solicitudSoporteTecnico, dejarResenasCalificaciones
                );
                
                permisoRepo.saveAll(todosLosPermisos);
                
                // ===== CREAR ROLES CON SUS PERMISOS =====
                
                // Rol Administrador
                Role administrador = crearRoleConPermisos("Administrador", Arrays.asList(
                    gestionarUsuarios, configurarPermisos, monitorizacionSistema, respaldarRestaurarDatos
                ));
                
                // Rol Gestor de Inventario (anteriormente "Inventario")
                Role gestorInventario = crearRoleConPermisos("Gestor de Inventario", Arrays.asList(
                    administrarEquipos, controlStock, gestionProveedores, generacionReportes
                ));
                
                // Rol Coordinador Logístico (anteriormente "Logistico")
                Role coordinadorLogistico = crearRoleConPermisos("Coordinador Logístico", Arrays.asList(
                    gestionPedidos, organizacionEntregas, planificacionMantenimiento, controlDevoluciones
                ));
                
                // Rol Soporte Técnico (anteriormente "Tecnico")
                Role soporteTecnico = crearRoleConPermisos("Soporte Técnico", Arrays.asList(
                    registroIncidencias, asignacionTecnicos, monitoreoSistema
                ));
                
                // Rol Cliente
                Role cliente = crearRoleConPermisos("Cliente", Arrays.asList(
                    registroClientes, busquedaEquipos, solicitudArriendoCompra, seguimientoPedidos,
                    gestionPerfil, solicitudSoporteTecnico, dejarResenasCalificaciones
                ));
                
                // Guardar todos los roles
                roleRepo.saveAll(Arrays.asList(
                    administrador, gestorInventario, coordinadorLogistico, soporteTecnico, cliente
                ));
                
                System.out.println("Datos iniciales cargados exitosamente:");
                System.out.println("- 5 roles creados");
                System.out.println("- 22 permisos creados");
                System.out.println("- Permisos asignados a roles correspondientes");
                
            } else {
                System.out.println("Datos ya existentes. No se cargaron nuevos datos");
            }
        };
    }
    
    // Método auxiliar para crear permisos
    private Permiso crearPermiso(String nombre, String descripcion) {
        Permiso permiso = new Permiso();
        permiso.setNombre(nombre);
        return permiso;
    }
    
    // Método auxiliar para crear roles con permisos
    private Role crearRoleConPermisos(String nombre, List<Permiso> permisos) {
        Role role = new Role();
        role.setNombre(nombre);
        role.setPermisos(new HashSet<>(permisos));
        return role;
    }
}

