package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.ProjectMember;
import ca.sait.backup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByUser(User user);
}
