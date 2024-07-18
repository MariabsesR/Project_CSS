package pt.ul.fc.css.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Thesis;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {
  Optional<Thesis> findByStudentId(Long studentId);
}
