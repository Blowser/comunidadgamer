package cl.duoc.comunidadgamer.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import cl.duoc.comunidadgamer.entity.LoginRequest;
import cl.duoc.comunidadgamer.entity.LoginResponse;
import cl.duoc.comunidadgamer.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getUsername());

        if (!passwordEncoder.matches(
                request.getPassword(),
                userDetails.getPassword())) {

            throw new RuntimeException(
                    "Credenciales inválidas");
        }

        String token =
        jwtService.generateToken(

                userDetails.getUsername(),

                userDetails.getAuthorities()
                        .stream()
                        .map(authority ->
                                authority.getAuthority())
                        .toList());

        return new LoginResponse(token);
    }
}