package ca.sait.backup.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Table(name = "support_ticket")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User complainant;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<SupportTicketChat> chat;

    @CreationTimestamp
    private Date creationDate;

    @Enumerated(EnumType.ORDINAL)
    private SupportTicketStatusEnum status;

    // General Information
    private double userRating;
    private String userFeedback;

    private String title;
    private String description;

}
