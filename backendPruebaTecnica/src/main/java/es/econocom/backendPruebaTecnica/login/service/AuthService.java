package es.econocom.backendPruebaTecnica.login.service;

import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Servicio que gestiona la autenticación de usuarios y la generación/validación de tokens JWT.
 *
 * Esta implementación es simulada y no se conecta a una base de datos real.
 */
@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    /**
     * Constructor que inyecta la utilidad de JWT.
     *
     * @param jwtUtil Clase utilitaria para gestionar tokens JWT.
     */
    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica al usuario verificando sus credenciales simuladas.
     * Si son válidas, genera y retorna un par de tokens (access y refresh).
     *
     * @param request Objeto con email y contraseña proporcionados por el usuario.
     * @return {@link AuthResponse} con tokens JWT válidos.
     * @throws RuntimeException si las credenciales son inválidas.
     */
    public AuthResponse authenticate(LoginRequest request) {

        System.out.println("DEBUG - prueba acceso" + request.getEmail() + " - " + request.getPassword());

        if ("admin@example.com".equals(request.getEmail()) && "1234".equals(request.getPassword())
                || "a".equals(request.getEmail()) && "a".equals(request.getPassword())) {

            String accessToken = jwtUtil.generateToken(request.getEmail());
            String refreshToken = UUID.randomUUID().toString(); // token simulado
            jwtUtil.storeRefreshToken(refreshToken, request.getEmail());
            return new AuthResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    /**
     * Genera un nuevo token de acceso usando un refresh token previamente emitido.
     *
     * @param refreshToken Token de actualización válido.
     * @return Nuevo token de acceso.
     * @throws RuntimeException si el refresh token no es válido o no está registrado.
     */
    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtil.getEmailFromRefreshToken(refreshToken);
        if (email != null) {
            return jwtUtil.generateToken(email);
        }
        throw new RuntimeException("Refresh token inválido");
    }

    /**
     * Valida un token JWT verificando su firma y expiración.
     *
     * @param token Token JWT a validar.
     * @return {@code true} si es válido; {@code false} en caso contrario.
     */
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    /**
     * Extrae el correo electrónico (subject) contenido en el token JWT.
     *
     * @param token Token JWT válido.
     * @return Correo electrónico extraído del token.
     */
    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }
}
