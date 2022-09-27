package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.SupportTicketChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TicketMessageRepository extends JpaRepository<SupportTicketChat, Long> {

}
