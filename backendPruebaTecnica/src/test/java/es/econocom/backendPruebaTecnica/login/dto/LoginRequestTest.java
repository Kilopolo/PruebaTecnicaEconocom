package es.econocom.backendPruebaTecnica.login.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginRequestTest {

    @Test
    void testSettersAndGetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("usuario@example.com");
        request.setPassword("miPassword123");

        assertEquals("usuario@example.com", request.getEmail());
        assertEquals("miPassword123", request.getPassword());
    }

    @Test
    void testToStringNotNull() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");
        assertNotNull(request.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginRequest req1 = new LoginRequest();
        req1.setEmail("a@b.com");
        req1.setPassword("pass");

        LoginRequest req2 = new LoginRequest();
        req2.setEmail("a@b.com");
        req2.setPassword("pass");

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
    }
}
