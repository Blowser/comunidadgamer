package cl.duoc.comunidadgamer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.comunidadgamer.entity.Evento;

@Service
public class EventoService {

    private final List<Evento> eventos = new ArrayList<>();

    private Long nextId = 1L;

    public List<Evento> getAllEventos() {
        return eventos;
    }

    public void agregarEvento(Evento evento) {

        evento.setId(nextId++);

        eventos.add(evento);
    }

    public Evento getEventoById(Long id) {

        for (Evento evento : eventos) {

            if (evento.getId() != null &&
                    evento.getId().equals(id)) {

                return evento;
            }
        }

        return null;
    }

    public void eliminarEvento(Long id) {

        eventos.removeIf(
                evento -> evento.getId() != null
                        && evento.getId().equals(id));
    }

    public void actualizarEvento(
            Long id,
            Evento eventoActualizado) {

        Evento evento = getEventoById(id);

        if (evento != null) {

            evento.setNombre(
                    eventoActualizado.getNombre());

            evento.setCategoria(
                    eventoActualizado.getCategoria());

            evento.setUbicacion(
                    eventoActualizado.getUbicacion());

            evento.setModalidad(
                    eventoActualizado.getModalidad());

            evento.setDescripcion(
                    eventoActualizado.getDescripcion());

            evento.setFecha(
                    eventoActualizado.getFecha());

            evento.setOrganizador(
                    eventoActualizado.getOrganizador());
        }
    }
}