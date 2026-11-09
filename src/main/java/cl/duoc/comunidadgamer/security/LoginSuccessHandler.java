package cl.duoc.comunidadgamer.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import cl.duoc.comunidadgamer.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public LoginSuccessHandler(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        String token =
                jwtService.generateToken(

                        authentication.getName(),

                        authentication.getAuthorities()
                                .stream()
                                .map(authority ->
                                        authority.getAuthority())
                                .toList());

        Cookie jwtCookie =
                new Cookie(
                        "jwtToken",
                        token);

        jwtCookie.setPath("/");

        jwtCookie.setHttpOnly(false);

        response.addCookie(
                jwtCookie);

        response.sendRedirect(
                "/home");
    }
}