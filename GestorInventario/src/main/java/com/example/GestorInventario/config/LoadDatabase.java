package com.example.GestorInventario.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.GestorInventario.model.Equipo;
import com.example.GestorInventario.model.Estado;
import com.example.GestorInventario.model.Marca;
import com.example.GestorInventario.model.Modelo;
import com.example.GestorInventario.repository.EquipoRepository;
import com.example.GestorInventario.repository.EstadoRepository;
import com.example.GestorInventario.webclient.MarcaModeloClient;

@Component
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(EquipoRepository equipoRepo, EstadoRepository estadoRepo, MarcaModeloClient marcaModeloClient) {
        return args -> {
            if (equipoRepo.count() == 0 && estadoRepo.count() == 0){
                // cargar datos iniciales
                Estado disponible = new Estado(null, "Disponible");
                Estado arrendado = new Estado(null, "Arrendado");
                Estado vendido = new Estado(null, "Vendido");
                Estado enMantenimiento = new Estado(null, "En mantenimiento");
                Estado enRevision = new Estado(null, "En revisión");
                Estado danado = new Estado(null, "Dañado");
                Estado enTransito = new Estado(null, "En tránsito");
                Estado pendienteEntrega = new Estado(null, "Pendiente de entrega");
                Estado pendienteRecoleccion = new Estado(null, "Pendiente de recolección");
                estadoRepo.saveAll(List.of(disponible, arrendado, vendido, enMantenimiento, enRevision, danado, enTransito, pendienteEntrega, pendienteRecoleccion));

                //obtener marcas y modelos desde otro microservicio
                List<Marca> marcas = marcaModeloClient.getAllMarcas();
                List<Modelo> modelos = marcaModeloClient.getAllModelos();

                // Crear equipos y guardarlos en la base de datos
                Equipo tractorJD5055E = new Equipo( "Tractor John Deere 5055E", 650000.0, 2500.0, "JD5055E-A", 1,1, disponible);
                Equipo cosechadoraJDS680 = new Equipo("Cosechadora John Deere S680", 1200000.0, 5000.0, "JD680-C", 2,2, arrendado);
                Equipo tractorNHT6050 = new Equipo( "Tractor New Holland T6050", 700000.0, 2700.0, "NHT6050-A", 3,3, enMantenimiento);
                Equipo cosechadoraNHCR790 = new Equipo( "Cosechadora New Holland CR7.90", 1100000.0, 4800.0, "NHCR790-B", 4,4, disponible);
                Equipo tractorCasePuma185 = new Equipo( "Tractor Case IH Puma 185", 780000.0, 2900.0, "CHPUMA185-A", 5,5, disponible);
                Equipo sembradoraCase1230 = new Equipo( "Sembradora Case IH 1230", 950000.0, 4300.0, "CH1230-S", 6,6, disponible);
                Equipo pulverizadoraJDR4045 = new Equipo( "Pulverizadora John Deere R4045", 600000.0, 2400.0, "JD4045-P", 7,7, pendienteEntrega);

                equipoRepo.saveAll(List.of(
                    tractorJD5055E, cosechadoraJDS680, tractorNHT6050, cosechadoraNHCR790,
                    tractorCasePuma185, sembradoraCase1230, pulverizadoraJDR4045
                ));
                System.out.println("Equipos ");
              }else {
                System.out.println("Ya existen datos en la base. No se cargó nada nuevo.");
                }  

};
    }
}
