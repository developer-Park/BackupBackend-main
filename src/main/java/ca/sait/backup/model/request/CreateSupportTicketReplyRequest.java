package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class CreateSupportTicketReplyRequest {
    private String message;
    private Long supportTicketId;
}
