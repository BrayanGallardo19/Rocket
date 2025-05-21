package com.example.SoporteTecnico.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SoporteTecnico.model.Ticket;
import com.example.SoporteTecnico.model.TipoSoporte;
import com.example.SoporteTecnico.repository.SoporteRepository;
import com.example.SoporteTecnico.repository.TicketRepository;
import com.example.SoporteTecnico.repository.TipoSoporteRepository;
import com.example.SoporteTecnico.webclient.UsuarioUser;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SoporteTecnicoService {


    @Autowired
    private SoporteRepository soporteRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TipoSoporteRepository tipoSoporteRepository;
    @Autowired
    private UsuarioUser usuarioUser;

    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }

    public Ticket saveTicket(Ticket tk){
        Map<String,Object> usuario = usuarioUser.getUsuarioPorId(tk.getId_usuario());
        if(usuario == null || usuario.isEmpty()){
            throw new RuntimeException("usuario no encontrado");
        }
        return ticketRepository.save(tk);
    }

    public Ticket getTicket(Integer id){
     return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket no encontrado" + id));
    }


        public boolean deleteTicketById(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    
    }

public Ticket actualizarTicket(Integer id, Ticket nuevoTicket) {

    if (nuevoTicket == null) {
        throw new IllegalArgumentException("El ticket enviado es nulo");
    }

    // Validación del ID del usuario
    Integer idUsuario = nuevoTicket.getId_usuario();
    if (idUsuario == null || idUsuario <= 0) {
        throw new IllegalArgumentException("El ID de usuario no es válido");
    }

    // Obtener y verificar existencia del usuario
    Map<String, Object> usuario = usuarioUser.getUsuarioPorId(idUsuario);
    if (usuario == null || usuario.isEmpty()) {
        throw new EntityNotFoundException("El usuario con ID " + idUsuario + " no existe");
    }

    // Obtener ticket existente
    Ticket existente = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket con ID " + id + " no encontrado"));

    // Validación de campos
    if (nuevoTicket.getFecha_inicio() == null) {
        throw new IllegalArgumentException("La fecha de inicio es obligatoria");
    }

    // Actualizar campos
    existente.setFecha_inicio(nuevoTicket.getFecha_inicio());
    existente.setFecha_cierre(nuevoTicket.getFecha_cierre());
    existente.setDescripcion(nuevoTicket.getDescripcion());
    existente.setId_usuario(idUsuario);

    return ticketRepository.save(existente);
}



}

