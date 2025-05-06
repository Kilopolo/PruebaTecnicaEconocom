package es.econocom.backendPruebaTecnica.login.service;


import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Lógica de autenticación simulada (sin base de datos)
@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(LoginRequest request) {

        System.out.println("DEBUG - prueba acceso"+ request.getEmail() + " - " + request.getPassword());

        if ("admin@example.com".equals(request.getEmail()) && "1234".equals(request.getPassword())
            || "a".equals(request.getEmail()) && "a".equals(request.getPassword()) ) {
            String accessToken = jwtUtil.generateToken(request.getEmail());
            String refreshToken = UUID.randomUUID().toString(); // token simulado
            jwtUtil.storeRefreshToken(refreshToken, request.getEmail()); // simulado
            return new AuthResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtil.getEmailFromRefreshToken(refreshToken);
        if (email != null) {
            return jwtUtil.generateToken(email);
        }
        throw new RuntimeException("Refresh token inválido");
    }


/*    public AuthResponse generateTokenFromSSO(String email) {
        String token = jwtUtil.generateToken(email);
        return new AuthResponse(token);
    }*/

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }
}