package es.econocom.backendPruebaTecnica.login.entity;


import jakarta.persistence.*;
import lombok.Data;

/**
 * Representa un usuario en la base de datos.
 */
@Data
@Entity
@Table(name = "usuarios") // Nombre de la tabla en la base de datos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    // Constructor vacío para JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
