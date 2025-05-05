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
        System.out.println("DEBUG - prueba acceso"+ request.getEmail() + " - " + request.getPassword());

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

    @GetMapping("/sso")
    public ResponseEntity<?> ssoRedirect
            (@RequestParam(defaultValue = "http://localhost:4200/sso/callback") String redirect_uri) {
        // Simula proveedor externo de SSO, devuelve una redirección con un código
        String fakeCode = "sso-1234";
        String redirectUrl = redirect_uri + "?code=" + fakeCode;

        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }

    @GetMapping("/sso/callback")
    public ResponseEntity<?> ssoCallback(@RequestParam String code) {
        // Validar el "code" simulado
        if ("sso-1234".equals(code)) {
            String token = String.valueOf(authService.generateTokenFromSSO("sso-user@example.com"));
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(401).body("Código SSO inválido");
        }
    }

}