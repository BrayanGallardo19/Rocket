package com.example.GestorPedidos.service;

import org.springframework.stereotype.Service;

import com.example.GestorPedidos.dto.EquipoDTO;
import com.example.GestorPedidos.dto.MarcaDTO;
import com.example.GestorPedidos.dto.ModeloDTO;
import com.example.GestorPedidos.dto.PedidoCompletoDTO;
import com.example.GestorPedidos.dto.TipoDTO;
import com.example.GestorPedidos.dto.UsuarioDTO;
import com.example.GestorPedidos.model.Pedido;
import com.example.GestorPedidos.model.Tipo;
import com.example.GestorPedidos.repository.PedidoRepository;
import com.example.GestorPedidos.repository.TipoRepository;
import com.example.GestorPedidos.webclient.EquipoClient;
import com.example.GestorPedidos.webclient.UsuarioClient;

@Service
public class PedidoDTOService {
    private final PedidoRepository pedidoRepository;
    private final TipoRepository tipoRepository;
    private final UsuarioClient usuarioClient;
    private final EquipoClient equipoClient;

    public PedidoDTOService(
            PedidoRepository pedidoRepository,
            TipoRepository tipoRepository,
            UsuarioClient usuarioClient,
            EquipoClient equipoClient) {
        this.pedidoRepository = pedidoRepository;
        this.tipoRepository = tipoRepository;
        this.usuarioClient = usuarioClient;
        this.equipoClient = equipoClient;
    }
    // metodo para construir un PedidoCompletoDTO a partir de un idPedido
    public PedidoCompletoDTO construirPedidoCompleto(Integer idPedido) {
        
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        if (pedido.getTipo() == null) {
            throw new RuntimeException("El pedido no tiene un tipo asociado");
        }
        Integer idTipo = pedido.getTipo().getIdTipo();
        Tipo tipo = tipoRepository.findById(idTipo)
        .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(pedido.getIdUsuario());
        EquipoDTO equipo = equipoClient.obtenerEquipoPorIdDTO(pedido.getIdEquipo());

        MarcaDTO marca = equipo.getMarca();
        ModeloDTO modelo = equipo.getModelo();

        PedidoCompletoDTO dto = new PedidoCompletoDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setFecha(pedido.getFechaPedido());
        dto.setTotal(pedido.getTotal());

        dto.setTipo(new TipoDTO(tipo.getIdTipo(), tipo.getNombre()));
        dto.setUsuario(usuario);
        dto.setEquipo(equipo);
        dto.setMarca(marca);
        dto.setModelo(modelo);

        return dto;
    }

    // metodo para obtener equipo por id
    public EquipoDTO obtenerEquipoPorId(Integer idEquipo) {
    return equipoClient.obtenerEquipoPorIdDTO(idEquipo);
}
}
