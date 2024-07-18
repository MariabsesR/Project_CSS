package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.entities.ThesisTheme;

public interface ThesisThemeRepository extends JpaRepository<ThesisTheme, Long> {
  ThesisTheme getThesisThemeById(Long id);
}
