package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Jury;

public interface JuryRepository extends JpaRepository<Jury, Long> {}
