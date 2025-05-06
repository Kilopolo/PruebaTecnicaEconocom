package es.econocom.backendPruebaTecnica.login.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) que representa la respuesta de autenticación
 * enviada al cliente tras un login exitoso o una renovación de token.
 *
 * Contiene un token de acceso (JWT) y un refresh token.
 */
@Data
public class AuthResponse {

    /**
     * Token JWT que se utiliza para autenticar futuras peticiones del cliente.
     */
    private String accessToken;

    /**
     * Token de renovación que permite obtener un nuevo token de acceso cuando este expire.
     */
    private String refreshToken;

    /**
     * Constructor para inicializar la respuesta de autenticación con los tokens generados.
     *
     * @param accessToken Token de acceso (JWT)
     * @param refreshToken Token de renovación
     */
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
