package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.AssetFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetFolderRepository extends JpaRepository<AssetFolder, Long> {
    List<AssetFolder> findByParent(AssetFolder parent);
}
