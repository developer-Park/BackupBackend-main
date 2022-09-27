package ca.sait.backup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Table(name = "asset_security_profile")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AssetSecurityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationships
    @OneToMany(mappedBy = "securityProfile", cascade = CascadeType.ALL)
    private List<AssetSecurityApproval> approvalList;

    @OneToOne(mappedBy = "securityProfile")
    private Asset asset;

    @OneToOne(mappedBy = "securityProfile")
    private AssetFolder assetFolder;

    @OneToMany(mappedBy = "securityProfile", cascade = CascadeType.ALL)
    private List<AssetSecurityRequest> requests;

    // TODO: Should not have this here, temporary ;)
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Security settings
    @Enumerated(EnumType.STRING)
    private AssetSecurityProfileTypeEnum securityType;

    private String securityConfiguration;

}
