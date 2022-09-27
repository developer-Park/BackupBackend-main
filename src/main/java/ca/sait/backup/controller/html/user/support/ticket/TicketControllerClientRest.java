package ca.sait.backup.controller.html.user.support.ticket;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.*;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/pri/user/ticket")
public class TicketControllerClientRest {

    @Autowired
    private UserService userService;

    @Autowired
    private SupportTicketService ticketService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/create")
    public JsonData createNewTicket(@RequestBody CreateNewSupportTicketRequest createRequest, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );

        SupportTicket supportTicket = this.ticketService.createNewSupportTicket(
            user,
            createRequest
        );

        System.out.println("New support ticket created: " + supportTicket.getTitle());

        return JsonData.buildSuccess("");
    }


    @PostMapping("/status")
    public JsonData changeTicketStatus(@RequestBody ModifyTicketRequest modReq) {
        SupportTicket ticket = this.ticketService.mediator_GetTicketById(
                modReq.getTicketId()
        );

        ticket.setStatus(
            SupportTicketStatusEnum.valueOf(
                    modReq.getNewStatus().toUpperCase()
            )
        );

        this.ticketService.mediator_UpdateTicket(
                ticket
        );

        return JsonData.buildSuccess("");
    }

    @PostMapping("/createMessage")
    public JsonData changeTicketStatus(@RequestBody CreateSupportTicketReplyRequest msgReq, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );

        SupportTicket ticket = this.ticketService.mediator_GetTicketById(
            msgReq.getSupportTicketId()
        );

        SupportTicketChat message = this.ticketService.addNewMessage(
            user,
            msgReq
        );

        return JsonData.buildSuccess("");

    }

    @PostMapping("/checkFeedbackPending")
    public JsonData checkReviewPending(@RequestBody ModifyTicketRequest modReq, HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        // Get ticket information
        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );

        SupportTicket supportTicket = this.ticketService.mediator_GetTicketById(
            modReq.getTicketId()
        );

        // Check if the ticket is not resolved yet
        boolean isResolved = supportTicket.getStatus().equals(SupportTicketStatusEnum.CLOSED);
        if (!isResolved) {
            return JsonData.buildSuccess("false");
        }

        // Check if the current user is the owner of the ticket
        boolean isComplainant = supportTicket.getComplainant().getId().equals(user.getId());
        if (!isComplainant) {
            return JsonData.buildSuccess("false");
        }

        // If it is, check to see if they haven't left feedback already.
        if (supportTicket.getUserRating() == 0 && supportTicket.getUserFeedback() == null) {
            return JsonData.buildSuccess("true");
        }

        // They have already left feedback, so return true
        return JsonData.buildSuccess("false");

    }

    @PostMapping("/leaveFeedback")
    public JsonData leaveFeedback(@RequestBody SupportTicketFeedbackRequest msgReq, HttpServletRequest request) {
        this.ticketService.saveSupportTicketFeedback(
            msgReq
        );
        return JsonData.buildSuccess("");
    }


}
