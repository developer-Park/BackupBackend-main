package ca.sait.backup.model.entity;

import ca.sait.backup.model.entity.Project;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Data
@Table(name = "category")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Asset> assets;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<AssetFolder> assetFolders;

    private String name;
    private String description;

}
