package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class GetAllSupportTicketsForStatusRequest {
    private String status;
}
