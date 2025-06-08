
package com.example.GestionUsuarios.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String rut;
    private String username;
    private String password;
    private Integer idRol;
}
