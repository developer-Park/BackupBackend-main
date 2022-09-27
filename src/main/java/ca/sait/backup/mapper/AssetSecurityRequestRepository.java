package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.AssetSecurityRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AssetSecurityRequestRepository extends JpaRepository<AssetSecurityRequest, Long> {

}
