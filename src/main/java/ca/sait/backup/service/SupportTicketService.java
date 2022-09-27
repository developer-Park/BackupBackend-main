package ca.sait.backup.service;


import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewSupportTicketRequest;
import ca.sait.backup.model.request.CreateSupportTicketReplyRequest;
import ca.sait.backup.model.request.SupportTicketFeedbackRequest;

import java.util.List;

public interface SupportTicketService {

    SupportTicket createNewSupportTicket(User user, CreateNewSupportTicketRequest supportTicket);
    List<SupportTicket> getSupportTicketsForUser(User user);

    SupportTicketChat addNewMessage(User sendingUser, CreateSupportTicketReplyRequest messageRequest);

    void saveSupportTicketFeedback(SupportTicketFeedbackRequest feedback);

    // Mediator specific functions
    List<SupportTicket> mediator_GetAllTicketsWithStatus(SupportTicketStatusEnum status);

    SupportTicket mediator_UpdateTicket(SupportTicket ticket);
    SupportTicket mediator_GetTicketById(Long id);


}
