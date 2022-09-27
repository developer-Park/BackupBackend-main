package ca.sait.backup.controller.html.mediator;


import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mediator")
public class MediatorDashboardController {

    @Autowired
    private SupportTicketService ticketService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String GetDashboard(HttpServletRequest request, Model model) {

        final int MAX_RECENT_TICKETS = 7;

        this.sessionService.exposeEssentialVariables(
            request,
            model
        );

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        // Grab some open tickets
        List<SupportTicket> recentTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.OPEN
        );

        // Check if there's not enough tickets
        if (recentTickets.size() < MAX_RECENT_TICKETS) {
            // Add stale tickets as well
            recentTickets.addAll(
                this.ticketService.mediator_GetAllTicketsWithStatus(
                    SupportTicketStatusEnum.PENDING
                )
            );
        }

        // We want to limit the number of tickets to only 7 max
        if (recentTickets.size() > MAX_RECENT_TICKETS) {
            recentTickets = recentTickets.subList(0, MAX_RECENT_TICKETS);
        }

        // Now let's get this mediator's ratings and reviews from past tickets

        List<SupportTicket> closedTickets = this.ticketService.mediator_GetAllTicketsWithStatus(
            SupportTicketStatusEnum.CLOSED
        );

        Long mediatorId = sessionContainer.getUserId();
        int numTicketsParticipated = 0;
        ArrayList<SupportTicket> ticketsWithFeedback = new ArrayList<SupportTicket>();
        Long numUsers = this.userRepository.count();
        Long numTickets = this.userRepository.count();

        double ratingSum = 0;
        double averageRating = 0;

        // TODO: This is inefficient, could optimize in the future.
        for (SupportTicket closedTicket: closedTickets) {

            // Check if this mediator is a participant (through text messages)
            boolean isParticipant = closedTicket.getChat().stream().filter( (SupportTicketChat msg) -> {
               if (msg.getFrom().getId().equals(mediatorId)) {
                   return true;
               }
               return false;
            }).count() > 0;

            // If not a participant we don't care
            if (!isParticipant) {
                continue;
            }else {
                numTicketsParticipated++;
            }

            // If no feedback, skip
            if (closedTicket.getUserRating() == 0 && closedTicket.getUserFeedback() == null) continue;

            ratingSum += closedTicket.getUserRating();

            ticketsWithFeedback.add(
                closedTicket
            );

        }

        // Get average rating
        if (ratingSum != 0) {
            averageRating = ratingSum / ticketsWithFeedback.size();
        }else {
            averageRating = 5.0;
        }

        String formattedRating = String.format("%.2f", averageRating);

        model.addAttribute("mediatorRatingAvg", formattedRating);
        model.addAttribute("numTickets", numTickets);
        model.addAttribute("numUsers", numUsers);
        model.addAttribute("ticketsWithFeedback", ticketsWithFeedback);
        model.addAttribute("numTicketsParticipated", numTicketsParticipated);
        model.addAttribute("tickets", recentTickets);

        return("/mediator/dashboard");
    }



}
