package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
