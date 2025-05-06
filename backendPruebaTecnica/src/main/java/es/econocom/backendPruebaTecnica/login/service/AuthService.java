package es.econocom.backendPruebaTecnica.login.service;

import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.entity.Usuario;
import es.econocom.backendPruebaTecnica.login.repository.UsuarioRepository;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Servicio que gestiona la autenticación de usuarios y la generación/validación de tokens JWT.
 */
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthService(JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
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
        // Buscar usuario en la base de datos por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());
        System.out.println("DEBUG - Usuario encontrado? " + usuarioOpt.isPresent());

        Usuario usuario = usuarioOpt.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que la contraseña sea correcta
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar y devolver los tokens
        String accessToken = jwtUtil.generateToken(usuario.getEmail());
        String refreshToken = UUID.randomUUID().toString();
        jwtUtil.storeRefreshToken(refreshToken, usuario.getEmail());

        return new AuthResponse(accessToken, refreshToken);
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
