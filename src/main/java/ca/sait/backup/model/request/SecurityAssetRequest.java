package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class SecurityAssetRequest {
    private Long assetId;
    private String assetType;
    private String message;
}
