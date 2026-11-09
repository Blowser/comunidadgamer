package cl.duoc.comunidadgamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComunidadgamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunidadgamerApplication.class, args);
	}

}

/*
 * Flujo de autenticación:
 *
 * Login
 * ↓
 * Spring Security
 * ↓
 * JWT
 * ↓
 * Cookie
 * ↓
 * JwtAuthorizationFilter
 * ↓
 * Acceso a rutas protegidas
 */
