package es.econocom.backendPruebaTecnica.login.controller;

// AuthController.java
import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador de autenticación
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

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
}