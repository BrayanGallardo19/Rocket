package com.example.GestorInventario.security;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.GestorInventario.webclient.UsuarioConectadoClient;

@Component
public class SeguridadService {

    private final UsuarioConectadoClient usuarioConectadoClient;

    public SeguridadService(UsuarioConectadoClient usuarioConectadoClient) {
        this.usuarioConectadoClient = usuarioConectadoClient;
    }

    public Integer obtenerUserIdDesdeToken(String token) {
        Map<String, Object> conectado = usuarioConectadoClient.obtenerUsuarioConectadoPorToken(token);
        return (Integer) conectado.get("userId");
        
        Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(userId);
    }

}
