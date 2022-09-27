package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class TryUnlockDataLockerRequest {
    private String password;
    private Long assetId;
    private String assetType;
}
