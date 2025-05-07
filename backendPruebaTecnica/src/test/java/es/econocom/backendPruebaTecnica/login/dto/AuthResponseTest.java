package es.econocom.backendPruebaTecnica.login.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthResponseTest {

    @Test
    void testConstructorAndGetters() {
        String accessToken = "access-token-example";
        String refreshToken = "refresh-token-example";

        AuthResponse response = new AuthResponse(accessToken, refreshToken);

        assertEquals(accessToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }

    @Test
    void testSetters() {
        AuthResponse response = new AuthResponse(null, null);

        response.setAccessToken("new-access-token");
        response.setRefreshToken("new-refresh-token");

        assertEquals("new-access-token", response.getAccessToken());
        assertEquals("new-refresh-token", response.getRefreshToken());
    }

    @Test
    void testToStringNotNull() {
        AuthResponse response = new AuthResponse("token1", "token2");
        assertNotNull(response.toString()); // Verifica que toString no devuelve null
    }

    @Test
    void testEqualsAndHashCode() {
        AuthResponse r1 = new AuthResponse("tokenA", "tokenB");
        AuthResponse r2 = new AuthResponse("tokenA", "tokenB");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
