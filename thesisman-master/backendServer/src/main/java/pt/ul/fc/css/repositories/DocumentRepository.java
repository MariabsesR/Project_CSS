package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {}
