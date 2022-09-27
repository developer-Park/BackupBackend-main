package ca.sait.backup.model.request;

import lombok.Data;

/**
 * Login request
 */

@Data
public class LoginRequest {

    private String email;

    private String password;

}
