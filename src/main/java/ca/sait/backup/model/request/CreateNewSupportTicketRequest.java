package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class CreateNewSupportTicketRequest {
    private String title;
    private String description;
}
