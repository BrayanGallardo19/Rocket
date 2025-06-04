package com.example.GestorPedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String rut;
    private String username;
    private String idRol;

}
