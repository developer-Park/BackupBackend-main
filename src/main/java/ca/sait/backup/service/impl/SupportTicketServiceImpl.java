package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.TicketMessageRepository;
import ca.sait.backup.mapper.TicketRepository;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewSupportTicketRequest;
import ca.sait.backup.model.request.CreateSupportTicketReplyRequest;
import ca.sait.backup.model.request.SupportTicketFeedbackRequest;
import ca.sait.backup.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated //Ticket validated write at least five letters of title and description
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final TicketRepository ticketRepository;

    private final TicketMessageRepository ticketChatRepository;

    @Override
    public SupportTicket createNewSupportTicket(User user, CreateNewSupportTicketRequest req) {

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setComplainant(user);
        supportTicket.setTitle(req.getTitle());
        supportTicket.setDescription(req.getDescription());
        supportTicket.setStatus(
            SupportTicketStatusEnum.OPEN
        );

        this.ticketRepository.save(supportTicket);

        // We also need to set the description as the initial message
        SupportTicketChat initialMessage = new SupportTicketChat();

        initialMessage.setMessage(
            req.getDescription()
        );
        initialMessage.setFrom(
            user
        );
        initialMessage.setTicket(supportTicket);

        this.ticketChatRepository.save(
            initialMessage
        );

        return supportTicket;
    }

    @Override
    public List<SupportTicket> getSupportTicketsForUser(User user) {
        //Check support ticket by user Complainant
        return ticketRepository.findAllByComplainant(user);

    }
    //Writer: Park
    public List<SupportTicket> getSupportTicketsForUserId(Long userId) {
        return ticketRepository.findAllByComplainantId(userId);
    }

    @Override
    public void saveSupportTicketFeedback(SupportTicketFeedbackRequest feedback) {

        SupportTicket ticket = this.ticketRepository.findOneById(
            feedback.getTicketId()
        );
        ticket.setUserRating(
            feedback.getTicketRating()
        );
        ticket.setUserFeedback(
            feedback.getMessage()
        );

        this.ticketRepository.save(
            ticket
        );

    }

    @Override
    public SupportTicketChat addNewMessage(User sendingUser, CreateSupportTicketReplyRequest messageRequest) {

        // Get ticket from id
        SupportTicket supportTicket = this.ticketRepository.findOneById(
            messageRequest.getSupportTicketId()
        );

        // Create new support ticket chat instance
        SupportTicketChat ticketMessage = new SupportTicketChat();

        // Populate the message
        ticketMessage.setMessage(
            messageRequest.getMessage()
        );
        ticketMessage.setFrom(
            sendingUser
        );
        ticketMessage.setTicket(
            supportTicket
        );

        // Save new message
        this.ticketChatRepository.save(
            ticketMessage
        );

        // Return new message
        return ticketMessage;

    }

    @Override
    public List<SupportTicket> mediator_GetAllTicketsWithStatus(SupportTicketStatusEnum status) {

        List<SupportTicket> tickets = this.ticketRepository.findAllByStatus(
            status
        );

        return tickets;

    }

    @Override
    public SupportTicket mediator_GetTicketById(Long id) {
        SupportTicket ticket = this.ticketRepository.findOneById(
            id
        );
        return ticket;
    }


    @Override
    public SupportTicket mediator_UpdateTicket(SupportTicket ticket) {
        this.ticketRepository.save(
            ticket
        );
        return ticket;
    }

}
