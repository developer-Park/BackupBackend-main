package ca.sait.backup.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Table(name = "support_ticket_chat")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "support_ticket_id")
    private SupportTicket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User from;

    @CreationTimestamp
    private Date creationDate;
    private String message;
}
