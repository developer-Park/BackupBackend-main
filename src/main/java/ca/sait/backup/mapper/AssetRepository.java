package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Asset;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AssetRepository extends JpaRepository<Asset, Long> {

}
