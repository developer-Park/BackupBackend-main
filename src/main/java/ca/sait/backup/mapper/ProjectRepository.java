package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
