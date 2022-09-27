package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.AssetSecurityApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AssetSecurityApprovalRepository extends JpaRepository<AssetSecurityApproval, Long> {

}
