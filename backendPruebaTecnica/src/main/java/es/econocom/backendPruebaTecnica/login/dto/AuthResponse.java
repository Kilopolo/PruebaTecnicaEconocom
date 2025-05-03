package es.econocom.backendPruebaTecnica.login.dto;


import lombok.Getter;

// AuthResponse.java
@Getter
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

}
