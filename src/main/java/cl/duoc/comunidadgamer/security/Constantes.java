package cl.duoc.comunidadgamer.security;

public final class Constantes {

    private Constantes() {
    }

    // Spring Security

    public static final String LOGIN_URL = "/login";

    public static final String HEADER_AUTHORIZATION_KEY =
            "Authorization";

    public static final String TOKEN_BEARER_PREFIX =
            "Bearer ";

    // JWT

    public static final String ISSUER_INFO =
            "https://www.comunidadgamer.cl/";

    public static final String SUPER_SECRET_KEY =
            "ComunidadGamerSuperSecretKeyParaJWT2026";

    public static final long TOKEN_EXPIRATION_TIME =
            3600000;
}