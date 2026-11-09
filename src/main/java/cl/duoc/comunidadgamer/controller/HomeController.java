package cl.duoc.comunidadgamer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {


    @GetMapping("/home")
    public String home(
            @RequestParam(name = "name",
            required = false,
            defaultValue = "Comunidad Gamer")
            String name,
            Model model) {

        model.addAttribute("name", name);

        return "Home";
    }

    @GetMapping("/")
    public String root(
            @RequestParam(name = "name",
            required = false,
            defaultValue = "Comunidad Gamer")
            String name,
            Model model) {

        model.addAttribute("name", name);

        return "Home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil";
    }

}