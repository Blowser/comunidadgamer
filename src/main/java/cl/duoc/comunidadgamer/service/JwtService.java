package cl.duoc.comunidadgamer.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.duoc.comunidadgamer.security.Constantes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key key =
            Keys.hmacShaKeyFor(
                    Constantes.SUPER_SECRET_KEY.getBytes());

    public String generateToken(
            String username,
            List<String> authorities) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put(
                "authorities",
                authorities);

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(username)

                .setIssuedAt(
                        new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + Constantes.TOKEN_EXPIRATION_TIME))

                .signWith(
                        key,
                        SignatureAlgorithm.HS256)

                .compact();
    }

    public String extractUsername(
            String token) {

        return extractClaims(token)
                .getSubject();
    }

    public Claims extractClaims(
            String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(
            String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}