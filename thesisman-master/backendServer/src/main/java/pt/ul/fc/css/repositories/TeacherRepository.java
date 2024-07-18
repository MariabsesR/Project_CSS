package pt.ul.fc.css.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Optional<Teacher> findOptionalByUsername(String username);}
