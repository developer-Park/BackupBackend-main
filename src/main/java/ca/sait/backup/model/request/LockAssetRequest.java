package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class LockAssetRequest {
    private Long assetId;
    private String assetType;
    private String lockType;
    private String lockConfiguration;
}
