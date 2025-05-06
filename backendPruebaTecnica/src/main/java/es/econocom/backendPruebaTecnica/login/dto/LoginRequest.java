package es.econocom.backendPruebaTecnica.login.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) que encapsula las credenciales del usuario
 * utilizadas en la solicitud de inicio de sesión.
 */
@Data
public class LoginRequest {

    /**
     * Dirección de correo electrónico del usuario.
     */
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;

    // Los getters y setters son generados automáticamente por Lombok (@Data)
}
