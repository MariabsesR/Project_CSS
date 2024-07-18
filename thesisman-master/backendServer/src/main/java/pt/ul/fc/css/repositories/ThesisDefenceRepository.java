package pt.ul.fc.css.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.entities.Room;
import pt.ul.fc.css.entities.ThesisDefence;
import pt.ul.fc.css.entities.TimeSlot;

public interface ThesisDefenceRepository extends JpaRepository<ThesisDefence, Long> {

	boolean existsByRoomAndScheduledTime(Room room, TimeSlot scheduledTime);
	long countByDuration(int duration);
    long countByDurationAndGradeGreaterThanEqual(int duration, double grade);
}
