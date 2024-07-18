package pt.ul.fc.css.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Responsible;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

	Optional<Responsible> findOptionalByUsername(String creatorUsername);}
