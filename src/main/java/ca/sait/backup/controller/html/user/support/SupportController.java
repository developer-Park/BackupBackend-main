package ca.sait.backup.controller.html.user.support;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.User;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/support")
public class SupportController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SupportTicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String GetSupportHome(Model model, HttpServletRequest request) {

        this.sessionService.exposeEssentialVariables(request, model);

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );
        User user = this.userService.dev_GetUserByEmail(
            sessionContainer.getEmail()
        );
        List<SupportTicket> tickets = this.ticketService.getSupportTicketsForUser(
            user
        );

        tickets.sort((SupportTicket t1, SupportTicket t2) -> {
           return t1.getStatus().ordinal() - t2.getStatus().ordinal();
        });

        model.addAttribute("tickets", tickets);

        return "/user/support_dashboard.html";
    }

}
