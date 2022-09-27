package ca.sait.backup.controller.html.general;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.SupportTicketChat;
import ca.sait.backup.model.entity.SupportTicketStatusEnum;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewSupportTicketRequest;
import ca.sait.backup.model.request.CreateSupportTicketReplyRequest;
import ca.sait.backup.model.request.ModifyTicketRequest;
import ca.sait.backup.service.NotificationService;
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

@RestController
@RequestMapping("api/v1/pri/general/notification")
public class NotificationControllerRest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/clear")
    public JsonData clearNotifications(HttpServletRequest request) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        this.notificationService.backend_markAsRead(
            new User(sessionContainer.getUserId())
        );

        return JsonData.buildSuccess("");
    }


}
