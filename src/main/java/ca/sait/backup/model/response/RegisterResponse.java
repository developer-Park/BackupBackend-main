package ca.sait.backup.model.response;

import lombok.Data;
import lombok.Getter;

@Getter
public class RegisterResponse {
    private boolean creationStatus;
    private String responseMessage;

    public RegisterResponse(boolean creationStatus) {
        this.creationStatus = creationStatus;
    }
}
