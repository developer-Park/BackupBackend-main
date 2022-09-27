package ca.sait.backup.model.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private boolean creationStatus;
    private String responseMessage;
}
