package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class UnlockAssetRequest {
    private Long requestId;
    private boolean allowed;
}
