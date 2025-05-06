package es.econocom.backendPruebaTecnica.login.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para la generación, validación y manejo de tokens JWT.
 *
 * También gestiona un almacenamiento temporal de refresh tokens asociados a direcciones de correo electrónico.
 */
@Component
public class JwtUtil {

    /**
     * Clave secreta usada para firmar los JWT. (En producción debe almacenarse de forma segura).
     */
    private final String SECRET_KEY = "claveSuperSecretaParaJWT1234567890";

    /**
     * Tiempo de expiración del token en milisegundos (1 hora).
     */
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    /**
     * Almacenamiento temporal de refresh tokens y su asociación con direcciones de correo electrónico.
     * Simula una base de datos o repositorio seguro.
     */
    private final Map<String, String> refreshTokenStore = new HashMap<>();

    /**
     * Obtiene la clave de firma a partir de la clave secreta.
     *
     * @return Objeto {@link Key} para firmar y verificar JWTs.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Genera un token JWT firmado que contiene el correo electrónico del usuario como subject.
     *
     * @param email Dirección de correo electrónico del usuario autenticado.
     * @return Token JWT como cadena.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida un token JWT verificando su firma y expiración.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si el token es válido; {@code false} si es inválido o expirado.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrae el correo electrónico (subject) de un token JWT válido.
     *
     * @param token Token JWT del cual extraer el subject.
     * @return Dirección de correo electrónico contenida en el token.
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Almacena un refresh token asociado al correo electrónico del usuario.
     *
     * @param token Refresh token generado.
     * @param email Correo electrónico del usuario.
     */
    public void storeRefreshToken(String token, String email) {
        refreshTokenStore.put(token, email);
    }

    /**
     * Obtiene el correo electrónico asociado a un refresh token almacenado.
     *
     * @param token Refresh token recibido.
     * @return Correo electrónico asociado, o {@code null} si el token no es válido.
     */
    public String getEmailFromRefreshToken(String token) {
        return refreshTokenStore.get(token);
    }
}
