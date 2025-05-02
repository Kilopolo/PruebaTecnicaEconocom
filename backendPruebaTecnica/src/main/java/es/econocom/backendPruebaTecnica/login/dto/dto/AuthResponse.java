package es.econocom.backendPruebaTecnica.login.dto.dto;


// AuthResponse.java
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
