package pt.ul.fc.css.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsername(String username); // Directly returns Student

    Optional<Student> findOptionalByUsername(String username); // Returns Optional<Student>

    Optional<Student> findById(Long userId); // Existing method
}
