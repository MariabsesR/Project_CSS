package pt.ul.fc.css.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOptionalByUsername(String username);
	User findByUsernameAndPassword(String username, String password);

  User findByUsername(String username);
}
