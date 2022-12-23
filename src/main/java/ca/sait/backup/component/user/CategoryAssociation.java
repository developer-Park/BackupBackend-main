package ca.sait.backup.component.user;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CategoryAssociation {
    private Category category;
    private List<Asset> assetList;
    private List<AssetFolder> folderList;
}
