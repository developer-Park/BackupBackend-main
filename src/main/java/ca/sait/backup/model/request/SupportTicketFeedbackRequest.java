package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class SupportTicketFeedbackRequest {
    private Long ticketId;
    private double ticketRating;
    private String message;
}
