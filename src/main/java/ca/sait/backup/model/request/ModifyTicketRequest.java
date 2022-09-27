package ca.sait.backup.model.request;

import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import lombok.Data;

@Data
public class ModifyTicketRequest {
    private Long ticketId;
    private String newStatus;
}
