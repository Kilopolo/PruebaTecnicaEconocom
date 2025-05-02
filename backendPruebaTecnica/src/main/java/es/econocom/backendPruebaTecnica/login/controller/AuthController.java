package es.econocom.backendPruebaTecnica.login.controller;

// AuthController.java
import es.econocom.backendPruebaTecnica.login.dto.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import es.econocom.backendPruebaTecnica.login.dto.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        

        // Simulación de usuario "admin@example.com" con clave "1234"
        if ("admin@example.com".equals(request.getEmail()) && "1234".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            return ResponseEntity.ok("Token válido para: " + email);
        }
        return ResponseEntity.status(401).body("Token inválido o expirado");
    }
}
