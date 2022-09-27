package ca.sait.backup.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Data
@Table(name = "assetfolder")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AssetFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Asset> childAssets;

    @ManyToOne()
    @JoinColumn(name = "assetfolder_id")
    private AssetFolder parent;

    @OneToOne()
    @JoinColumn(name = "asset_security_profile_id")
    private AssetSecurityProfile securityProfile;

    @Expose
    private String name;

}
