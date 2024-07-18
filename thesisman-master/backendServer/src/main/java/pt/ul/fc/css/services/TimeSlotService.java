package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.TimeSlot;
import pt.ul.fc.css.repositories.TimeSlotRepository;

import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public TimeSlot getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id).orElse(null);
    }

    public void saveTimeSlot(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }

    public void deleteTimeSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }
}
