package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.entities.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {}
