package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
