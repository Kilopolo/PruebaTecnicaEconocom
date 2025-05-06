package es.econocom.backendPruebaTecnica.login.repository;


import es.econocom.backendPruebaTecnica.login.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para interactuar con la tabla de usuarios.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar un usuario por su correo electrónico
    Optional<Usuario> findByEmail(String email);
}