package es.econocom.backendPruebaTecnica.login.controller;

import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import es.econocom.backendPruebaTecnica.login.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Controlador REST responsable de manejar las operaciones de autenticación del usuario.
 * Proporciona endpoints para login, validación de token, autenticación SSO y renovación de tokens.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param authService Servicio de autenticación que maneja la lógica del login y validación de tokens.
     * @param jwtUtil Utilidad para la generación y validación de tokens JWT.
     */
    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint para iniciar sesión con credenciales de usuario.
     *
     * @param request Objeto que contiene el email y la contraseña del usuario.
     * @return ResponseEntity con el token JWT si es exitoso, o un error 401 si las credenciales son inválidas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.debug("DEBUG - prueba acceso{} - {}", request.getEmail(), request.getPassword());

        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    /**
     * Endpoint para validar un token JWT recibido en la cabecera Authorization.
     *
     * @param authHeader Cabecera HTTP con el token JWT (prefijado con "Bearer ").
     * @return ResponseEntity con mensaje de éxito o error 401 si el token es inválido o ha expirado.
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (authService.validateToken(token)) {
            String email = authService.extractEmail(token);
            return ResponseEntity.ok("Token válido para: " + email);
        } else {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }
    }

    /**
     * Endpoint que simula un proveedor externo de SSO.
     * Redirige al frontend con un código simulado.
     *
     * @param redirect_uri URI a la cual redirigir tras la autenticación SSO.
     * @return Redirección HTTP 302 con un código SSO simulado en los parámetros.
     */
    @GetMapping("/sso")
    public ResponseEntity<?> ssoRedirect(@RequestParam(defaultValue = "http://localhost:4200/sso/callback") String redirect_uri) {
        String fakeCode = "sso-1234";
        String redirectUrl = redirect_uri + "?code=" + fakeCode;

        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }

    /**
     * Endpoint que maneja el callback del proveedor SSO.
     * Genera un token de acceso si el código SSO es válido.
     *
     * @param body Mapa que contiene el código de autorización SSO.
     * @return Token JWT y refresh token si el código es válido, o error 401 si es inválido.
     */
    @PostMapping("/sso/callback")
    public ResponseEntity<?> ssoCallback(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        if ("sso-1234".equals(code)) {
            String email = "sso-user@example.com";
            String accessToken = jwtUtil.generateToken(email);
            String refreshToken = UUID.randomUUID().toString();
            jwtUtil.storeRefreshToken(refreshToken, email);
            AuthResponse response = new AuthResponse(accessToken, refreshToken);
            log.info("response={}->", response);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Código SSO inválido");
        }
    }

    /**
     * Endpoint para renovar el token de acceso usando un refresh token válido.
     *
     * @param body Mapa que contiene el refresh token.
     * @return Nuevo token JWT si el refresh token es válido, o error 401 si no lo es.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        try {
            String refreshToken = body.get("refreshToken");
            String newAccessToken = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Refresh token inválido");
        }
    }
}
