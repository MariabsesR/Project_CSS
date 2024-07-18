package pt.ul.fc.css.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.CompanyEmployee;
import pt.ul.fc.css.services.CompanyEmployeeDTO;

public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, Long> {
	Optional<CompanyEmployee> findByUsernameAndPassword(String username, String password);

	Optional<CompanyEmployee> findOptionalByUsername(String creatorUsername);
}
