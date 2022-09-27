package ca.sait.backup.service;


import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserNotification;
import ca.sait.backup.model.entity.UserNotificationEnum;

import java.util.List;

public interface NotificationService {
    void backend_createNotification(User user, UserNotificationEnum type, String body);
    public List<UserNotification> backend_getUnreadNotificationsForUser(User user);
    void backend_markAsRead(User user);
}
