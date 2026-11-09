package cl.duoc.comunidadgamer.security;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private Claims setSigningKey(
            HttpServletRequest request) {

        String jwtToken = request
                .getHeader(
                        Constantes.HEADER_AUTHORIZATION_KEY)
                .replace(
                        Constantes.TOKEN_BEARER_PREFIX,
                        "");

        Key key = Keys.hmacShaKeyFor(
                Constantes.SUPER_SECRET_KEY.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void setAuthentication(
            Claims claims) {

        List<String> authorities =
                (List<String>) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()));

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }

    private boolean isJWTValid(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authenticationHeader =
                request.getHeader(
                        Constantes.HEADER_AUTHORIZATION_KEY);

        if (authenticationHeader == null
                || !authenticationHeader.startsWith(
                        Constantes.TOKEN_BEARER_PREFIX)) {

            return false;
        }

        return true;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {

            if (isJWTValid(request, response)) {

                Claims claims =
                        setSigningKey(request);

                if (claims.get("authorities") != null) {

                    setAuthentication(claims);

                } else {

                    SecurityContextHolder.clearContext();
                }
            }

            filterChain.doFilter(
                    request,
                    response);

        } catch (Exception e) {

            SecurityContextHolder.clearContext();

            filterChain.doFilter(
                    request,
                    response);
        }
    }
}