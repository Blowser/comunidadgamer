package cl.duoc.comunidadgamer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @GetMapping("/secure")
    public String secure(
            @RequestParam(
                    defaultValue = "Invitado")
            String name) {

        return "Bienvenido " + name
                + ", acceso autorizado mediante JWT.";
    }
}