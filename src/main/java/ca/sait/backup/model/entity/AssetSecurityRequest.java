package ca.sait.backup.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Table(name = "asset_security_request")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AssetSecurityRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship
    @ManyToOne
    @JoinColumn(name = "asset_security_profile_id")
    private AssetSecurityProfile securityProfile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Attributes
    private String message;

    @Enumerated(EnumType.STRING)
    private AssetRequestStatusEnum status;

}
