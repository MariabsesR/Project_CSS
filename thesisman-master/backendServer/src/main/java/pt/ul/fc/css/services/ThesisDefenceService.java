package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.*;
import pt.ul.fc.css.repositories.ThesisDefenceRepository;
import pt.ul.fc.css.repositories.DocumentRepository;
import pt.ul.fc.css.repositories.RoomRepository;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.repositories.TimeSlotRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThesisDefenceService {

    @Autowired
    private ThesisDefenceRepository thesisDefenceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    public void scheduleDefence(Long documentId, boolean isInPerson, Long timeSlotId, int duration, Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (isInPerson && !roomOptional.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }

        Room room = roomOptional.orElse(null);

        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);
        if (!timeSlotOptional.isPresent()) {
            throw new IllegalArgumentException("Time slot not found");
        }
        TimeSlot timeSlot = timeSlotOptional.get();

        Optional<Document> documentOptional = documentRepository.findById(documentId);
        if (!documentOptional.isPresent()) {
            throw new IllegalArgumentException("Document not found");
        }
        Document document = documentOptional.get();

        ThesisDefence thesisDefence = new ThesisDefence(isInPerson, duration, timeSlot, document);
        thesisDefence.setRoom(room);

        thesisDefenceRepository.save(thesisDefence);
    }
    
    public void scheduleFinalDefence(Long documentId, boolean isInPerson, Long timeSlotId, int duration, Long roomId, Long presidentId, Long member1Id, Long member2Id) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (isInPerson && !roomOptional.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }

        Room room = roomOptional.orElse(null);

        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);
        if (!timeSlotOptional.isPresent()) {
            throw new IllegalArgumentException("Time slot not found");
        }
        TimeSlot timeSlot = timeSlotOptional.get();

        Optional<Document> documentOptional = documentRepository.findById(documentId);
        if (!documentOptional.isPresent()) {
            throw new IllegalArgumentException("Document not found");
        }
        Document document = documentOptional.get();

        // Create the jury
        Jury jury = new Jury();
        jury.setThesisDefences(new ThesisDefence(isInPerson, duration, timeSlot, document));
        jury.addTeacher(teacherRepository.findById(presidentId).orElseThrow(() -> new IllegalArgumentException("President teacher not found")));
        jury.addTeacher(teacherRepository.findById(member1Id).orElseThrow(() -> new IllegalArgumentException("Member 1 teacher not found")));
        jury.addTeacher(teacherRepository.findById(member2Id).orElseThrow(() -> new IllegalArgumentException("Member 2 teacher not found")));

        thesisDefenceRepository.save(jury.getThesisDefences());
    }
    
 // Method to check if the document is already scheduled for defence
    public boolean isDocumentScheduled(Long documentId) {
        Optional<Document> documentOptional = documentRepository.findById(documentId);
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            return thesisDefenceRepository.existsById(document.getId());
        }
        return false;
    }

    // Method to check if the room and time slot combination is already booked
    public boolean isRoomAndTimeSlotBooked(Long roomId, Long timeSlotId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);

        if (roomOptional.isPresent() && timeSlotOptional.isPresent()) {
            Room room = roomOptional.get();
            TimeSlot timeSlot = timeSlotOptional.get();
            return thesisDefenceRepository.existsByRoomAndScheduledTime(room, timeSlot);
        }
        return false;
    }


    public void insertGrade(Long defenceId, double grade) {
        Optional<ThesisDefence> thesisDefenceOptional = thesisDefenceRepository.findById(defenceId);
        if (!thesisDefenceOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid defence ID");
        }

        ThesisDefence thesisDefence = thesisDefenceOptional.get();
        thesisDefence.setGrade(grade);
        thesisDefenceRepository.save(thesisDefence);
    }
    
    public List<Long> getAllDefenceIds() {
        try {
            List<ThesisDefence> defenceList = thesisDefenceRepository.findAll();
            if (defenceList != null && !defenceList.isEmpty()) {
                return defenceList.stream().map(ThesisDefence::getId).collect(Collectors.toList());
            } else {
                // Handle case where defenceList is null or empty
                return Collections.emptyList(); // or throw an exception or handle it as appropriate
            }
        } catch (Exception e) {
            // Handle the exception appropriately, such as logging or re-throwing
            e.printStackTrace(); // Example: Print the stack trace for debugging
            return Collections.emptyList(); // or throw a custom exception or handle it as appropriate
        }
    }
    
    public List<Long> getAll60MinDefenceIds() {
        return thesisDefenceRepository.findAll().stream()
                                      .filter(defence -> defence.getDuration() == 60)
                                      .map(ThesisDefence::getId)
                                      .collect(Collectors.toList());
    }

    public List<Long> getAll90MinDefenceIds() {
        return thesisDefenceRepository.findAll().stream()
                                      .filter(defence -> defence.getDuration() == 90)
                                      .map(ThesisDefence::getId)
                                      .collect(Collectors.toList());
    }
}
