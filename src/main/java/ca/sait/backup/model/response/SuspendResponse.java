package ca.sait.backup.model.response;

import lombok.Getter;

@Getter
public class SuspendResponse {

    private boolean susPendStatus;

    public SuspendResponse(boolean susPendStatus) {
        this.susPendStatus = susPendStatus;
    }
}
