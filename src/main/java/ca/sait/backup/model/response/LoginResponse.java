package ca.sait.backup.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean authenticated;
    private String JWT;

    public LoginResponse(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
