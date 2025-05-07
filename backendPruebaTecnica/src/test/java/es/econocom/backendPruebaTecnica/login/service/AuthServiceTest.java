package es.econocom.backendPruebaTecnica.login.service;

import es.econocom.backendPruebaTecnica.login.dto.AuthResponse;
import es.econocom.backendPruebaTecnica.login.dto.LoginRequest;
import es.econocom.backendPruebaTecnica.login.entity.Usuario;
import es.econocom.backendPruebaTecnica.login.repository.UsuarioRepository;
import es.econocom.backendPruebaTecnica.login.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private JwtUtil jwtUtil;
    private UsuarioRepository usuarioRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        usuarioRepository = mock(UsuarioRepository.class);
        authService = new AuthService(jwtUtil, usuarioRepository);
    }

    @Test
    void testAuthenticateSuccess() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("1234");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("1234");

        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        when(jwtUtil.generateToken("test@example.com")).thenReturn("access-token");
        doNothing().when(jwtUtil).storeRefreshToken(anyString(), eq("test@example.com"));

        // When
        AuthResponse response = authService.authenticate(request);

        // Then
        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    void testAuthenticateInvalidPasswordThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrong");

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("correct");

        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        assertThrows(RuntimeException.class, () -> authService.authenticate(request));
    }

    @Test
    void testAuthenticateUserNotFoundThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("1234");

        when(usuarioRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.authenticate(request));
    }

    @Test
    void testRefreshAccessTokenSuccess() {
        when(jwtUtil.getEmailFromRefreshToken("valid-refresh")).thenReturn("test@example.com");
        when(jwtUtil.generateToken("test@example.com")).thenReturn("new-access-token");

        String result = authService.refreshAccessToken("valid-refresh");

        assertEquals("new-access-token", result);
    }

    @Test
    void testRefreshAccessTokenInvalid() {
        when(jwtUtil.getEmailFromRefreshToken("invalid-refresh")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> authService.refreshAccessToken("invalid-refresh"));
    }

    @Test
    void testValidateToken() {
        when(jwtUtil.validateToken("token")).thenReturn(true);
        assertTrue(authService.validateToken("token"));
    }

    @Test
    void testExtractEmail() {
        when(jwtUtil.extractEmail("token")).thenReturn("user@example.com");
        assertEquals("user@example.com", authService.extractEmail("token"));
    }
}
