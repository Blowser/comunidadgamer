package cl.duoc.comunidadgamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cl.duoc.comunidadgamer.entity.Evento;
import cl.duoc.comunidadgamer.service.EventoService;

@Controller
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/eventos")
    public String eventos(Model model) {

        model.addAttribute(
                "eventos",
                eventoService.getAllEventos());

        return "eventos";
    }

    @GetMapping("/evento-detalle/{id}")
    public String eventoDetalle(
            @PathVariable Long id,
            Model model) {

        Evento evento = eventoService.getEventoById(id);

        model.addAttribute("evento", evento);

        return "evento-detalle";
    }
    
    @GetMapping("/proximos-eventos")
    public String proximosEventos(
            Model model) {

        model.addAttribute(
                "eventos",
                eventoService.getAllEventos());

        return "proximos-eventos";
    }

    @GetMapping("/crear-evento")
    public String crearEvento() {
        return "crear-evento";
    }

    @PostMapping("/crear-evento")
    public String guardarEvento(
            @ModelAttribute Evento evento) {

        eventoService.agregarEvento(evento);

        return "redirect:/eventos";
    }

    @GetMapping("/eliminar-evento/{id}")
    public String eliminarEvento(
            @PathVariable Long id) {

        eventoService.eliminarEvento(id);

        return "redirect:/eventos";
    }

}