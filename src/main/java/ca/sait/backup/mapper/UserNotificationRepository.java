package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {


    List<UserNotification> findAllByUserAndViewed(User user, boolean viewed);


}
