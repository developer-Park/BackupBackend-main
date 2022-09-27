package ca.sait.backup.component.user;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



public class CategoryAssociation {
    @Getter @Setter private Category category;
    @Getter @Setter private List<Asset> assetList;
    @Getter @Setter private List<AssetFolder> folderList;
}
