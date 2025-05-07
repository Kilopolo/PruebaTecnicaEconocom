package es.econocom.backendPruebaTecnica.login.controller;

import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import es.econocom.backendPruebaTecnica.login.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        AuthResponse expectedResponse = new AuthResponse("access-token", "refresh-token");

        when(authService.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void login_failure() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrong");

        when(authService.authenticate(request)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.login(request);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales inválidas", response.getBody());
    }

    @Test
    void validate_success() {
        String token = "valid-token";
        String email = "user@example.com";

        when(authService.validateToken(token)).thenReturn(true);
        when(authService.extractEmail(token)).thenReturn(email);

        ResponseEntity<?> response = authController.validate("Bearer " + token);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Token válido para: " + email, response.getBody());
    }

    @Test
    void validate_failure() {
        String token = "invalid-token";

        when(authService.validateToken(token)).thenReturn(false);

        ResponseEntity<?> response = authController.validate("Bearer " + token);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o expirado", response.getBody());
    }

    @Test
    void ssoCallback_success() {
        Map<String, String> body = new HashMap<>();
        body.put("code", "sso-1234");

        String email = "sso-user@example.com";
        String accessToken = "access-token";
        String refreshToken = UUID.randomUUID().toString();

        when(jwtUtil.generateToken(email)).thenReturn(accessToken);

        ResponseEntity<?> response = authController.ssoCallback(body);

        assertEquals(OK, response.getStatusCode());
        AuthResponse authResponse = (AuthResponse) response.getBody();
        assertNotNull(authResponse);
        assertEquals(accessToken, authResponse.getAccessToken());
        assertNotNull(authResponse.getRefreshToken());
    }

    @Test
    void ssoCallback_failure() {
        Map<String, String> body = new HashMap<>();
        body.put("code", "invalid-code");

        ResponseEntity<?> response = authController.ssoCallback(body);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertEquals("Código SSO inválido", response.getBody());
    }

    @Test
    void refreshToken_success() {
        String refreshToken = "valid-refresh-token";
        String newAccessToken = "new-access-token";

        Map<String, String> body = Map.of("refreshToken", refreshToken);

        when(authService.refreshAccessToken(refreshToken)).thenReturn(newAccessToken);

        ResponseEntity<?> response = authController.refreshToken(body);

        assertEquals(OK, response.getStatusCode());
        AuthResponse authResponse = (AuthResponse) response.getBody();
        assertEquals(newAccessToken, authResponse.getAccessToken());
        assertEquals(refreshToken, authResponse.getRefreshToken());
    }

    @Test
    void refreshToken_failure() {
        String refreshToken = "invalid-refresh-token";

        Map<String, String> body = Map.of("refreshToken", refreshToken);

        when(authService.refreshAccessToken(refreshToken)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.refreshToken(body);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertEquals("Refresh token inválido", response.getBody());
    }
}
