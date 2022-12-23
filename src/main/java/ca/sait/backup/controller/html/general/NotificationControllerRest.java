package ca.sait.backup.controller.html.general;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.service.NotificationService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/pri/general/notification")
@RequiredArgsConstructor
public class NotificationControllerRest {

    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final NotificationService notificationService;

    @PostMapping("/clear")
    public JsonData clearNotifications(HttpServletRequest request) {
        JWTSessionContainer sessionContainer = sessionService.extractSession(request);
        notificationService.backend_markAsRead(new User(sessionContainer.getUserId()));
        return JsonData.buildSuccess("");
    }


}
