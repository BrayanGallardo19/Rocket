package com.example.GestorPedidos.model;

import lombok.Data;

@Data
//clase para la conexion con el microservicio de usuarios
public class Usuario {
    private Integer id;

   
    private String nombre;

  
    private String appaterno;
   
    private String apmaterno;
 
    private String rut;
 
    private String username;
   
    private String password;
 
    private String id_rol;
}
