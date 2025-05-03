package es.econocom.backendPruebaTecnica.login.service;


import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import org.springframework.stereotype.Service;

// Lógica de autenticación simulada (sin base de datos)
@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(LoginRequest request) {

        System.out.println("DEBUG - prueba acceso"+ request.getEmail() + " - " + request.getPassword());

        if ("admin@example.com".equals(request.getEmail()) && "1234".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            return new AuthResponse(token);
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }
}