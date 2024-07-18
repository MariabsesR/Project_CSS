package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Dissertation;

public interface DissertationRepository extends JpaRepository<Dissertation, Long> {
  // You can define additional methods here if needed
}
