package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String emailAddress);
   // Optional<User> findByEmail(String emailAddress);
    List<User> findByRole(UserRole role);



    User findByNameAndEmail(String name, String email);

}
