package es.econocom.backendPruebaTecnica.login.repository;

import es.econocom.backendPruebaTecnica.login.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Debe guardar y encontrar un usuario por email")
    void testFindByEmail() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setEmail("test@correo.com");
        usuario.setPassword("clave123");

        usuarioRepository.save(usuario);

        // When
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("test@correo.com");

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals("clave123", encontrado.get().getPassword());
    }

    @Test
    @DisplayName("No debe encontrar un usuario si el email no existe")
    void testFindByEmailNotFound() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("inexistente@correo.com");
        assertFalse(resultado.isPresent());
    }
}
