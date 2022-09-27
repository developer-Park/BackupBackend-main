package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.UserNotificationRepository;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserNotification;
import ca.sait.backup.model.entity.UserNotificationEnum;
import ca.sait.backup.service.NotificationService;
import ca.sait.backup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserNotificationRepository notificationRepository;

    @Override
    public void backend_createNotification(User user, UserNotificationEnum type, String body) {

        UserNotification uNotification = new UserNotification();
        uNotification.setUser(user);
        uNotification.setViewed(false);
        uNotification.setType(type);
        uNotification.setBody(body);

        this.notificationRepository.save(
            uNotification
        );
    }

    @Override
    public List<UserNotification> backend_getUnreadNotificationsForUser(User user) {
        return this.notificationRepository.findAllByUserAndViewed(
            user,
            false
        );
    }

    @Override
    public void backend_markAsRead(User user) {
        List<UserNotification> uNotifications = this.backend_getUnreadNotificationsForUser(
            user
        );

        for(UserNotification notif: uNotifications) {
            notif.setViewed(
                true
            );
        }

        this.notificationRepository.saveAll(
            uNotifications
        );
    }

}
