package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.services.ThesisDefenceService;
import pt.ul.fc.css.services.RoomService;
import pt.ul.fc.css.services.TeacherService;
import pt.ul.fc.css.services.TimeSlotService;
import pt.ul.fc.css.services.DocumentService;
import pt.ul.fc.css.entities.Room;
import pt.ul.fc.css.entities.TimeSlot;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.entities.Document;
import pt.ul.fc.css.entities.Teacher;
import pt.ul.fc.css.entities.ThesisDefence;
import pt.ul.fc.css.services.StatisticsService;

import java.util.List;

@Controller
@RequestMapping("/api")
public class AdvisorController {

    @Autowired
    private ThesisDefenceService thesisDefenceService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TimeSlotService timeSlotService;
    
    @Autowired
    private DocumentService documentService;

    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StatisticsService statisticsService;
    
    @GetMapping("/scheduleDefence")
    public String getScheduleDefencePage(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
        List<Room> rooms = roomService.getAllRooms();
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        List<Document> documents = documentService.getAllDocuments();
        model.addAttribute("rooms", rooms);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("documents", documents);
        return "schedule_defence";
    }

    @PostMapping("/scheduleDefence")
    public String scheduleDefence(@RequestParam Long documentId,
                                  @RequestParam boolean isInPerson,
                                  @RequestParam Long timeSlotId,
                                  @RequestParam int duration,
                                  @RequestParam(required = false) Long roomId,
                                  RedirectAttributes redirectAttributes) {

        // Check if the document is already scheduled for defence
        if (thesisDefenceService.isDocumentScheduled(documentId)) {
            redirectAttributes.addFlashAttribute("error", "The document is already scheduled for defence.");
            return "redirect:/api/scheduleDefence";
        }

        // Check if the room and time slot combination is already booked
        if (roomId != null && thesisDefenceService.isRoomAndTimeSlotBooked(roomId, timeSlotId)) {
            redirectAttributes.addFlashAttribute("error", "The selected room and time slot are already booked.");
            return "redirect:/api/scheduleDefence";
        }

        // Check if room is required but not provided
        if (isInPerson && roomId == null) {
            redirectAttributes.addFlashAttribute("error", "Room is required for in-person defence.");
            return "redirect:/api/scheduleDefence";
        }

        // Schedule the defence if all checks pass
        thesisDefenceService.scheduleDefence(documentId, isInPerson, timeSlotId, duration, roomId);
        redirectAttributes.addFlashAttribute("message", "Defence scheduled successfully.");
        return "redirect:/api/scheduleDefence";
    }



    @PostMapping("/insertGrade")
    public String insertGrade(@RequestParam Long defenceId, @RequestParam double grade, RedirectAttributes redirectAttributes) {
        thesisDefenceService.insertGrade(defenceId, grade);
        redirectAttributes.addFlashAttribute("message", "Grade recorded successfully"); // Add a flash attribute
        return "redirect:/api/insertGrade"; // Redirect to the appropriate path
    }
    
    @GetMapping("/insertGrade")
    public String showRecordGradeForm(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
    	List<Long> defenceIds = thesisDefenceService.getAll60MinDefenceIds(); // Assuming you have a service method to retrieve defence IDs
        model.addAttribute("defenceIds", defenceIds);
        return "insert_grade";
    }
    
    @PostMapping("/scheduleFinalDefence")
    public String scheduleFinalDefence(@RequestParam Long documentId,
                                       @RequestParam boolean isInPerson,
                                       @RequestParam Long timeSlotId,
                                       @RequestParam int duration,
                                       @RequestParam(required = false) Long roomId,
                                       @RequestParam Long presidentId,
                                       @RequestParam Long member1Id,
                                       @RequestParam Long member2Id,
                                       RedirectAttributes redirectAttributes) {

        // Check if the document is already scheduled for defense
        if (thesisDefenceService.isDocumentScheduled(documentId)) {
            redirectAttributes.addFlashAttribute("error", "The document is already scheduled for defense.");
            return "redirect:/api/scheduleFinalDefence";
        }

        // Check if the room and time slot combination is already booked
        if (roomId != null && thesisDefenceService.isRoomAndTimeSlotBooked(roomId, timeSlotId)) {
            redirectAttributes.addFlashAttribute("error", "The selected room and time slot are already booked.");
            return "redirect:/api/scheduleFinalDefence";
        }

        // Check if room is required but not provided
        if (isInPerson && roomId == null) {
            redirectAttributes.addFlashAttribute("error", "Room is required for in-person defense.");
            return "redirect:/api/scheduleFinalDefence";
        }

        // Schedule the final defense if all checks pass
        thesisDefenceService.scheduleFinalDefence(documentId, isInPerson, timeSlotId, duration, roomId, presidentId, member1Id, member2Id);
        redirectAttributes.addFlashAttribute("message", "Final defense scheduled successfully.");
        return "redirect:/api/scheduleFinalDefence";
    }


    @GetMapping("/scheduleFinalDefence")
    public String getScheduleFinalDefencePage(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
        List<Room> rooms = roomService.getAllRooms();
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        List<Document> documents = documentService.getAllDocuments();
        List<Teacher> teachers = teacherService.getAllTeacher();
        model.addAttribute("rooms", rooms);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("documents", documents);
        model.addAttribute("teachers", teachers);
        return "schedule_final_defence";
    }

    @GetMapping("/insertFinalGrade")
    public String showRecordFinalGradeForm(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
    	List<Long> defenceIds = thesisDefenceService.getAll90MinDefenceIds();
        model.addAttribute("defenceIds", defenceIds);
        return "insert_final_grade";
    }

    @PostMapping("/insertFinalGrade")
    public String recordFinalGrade(@RequestParam Long defenceId, @RequestParam double grade, RedirectAttributes redirectAttributes) {
        try {
            thesisDefenceService.insertGrade(defenceId, grade);
            redirectAttributes.addFlashAttribute("message", "Grade recorded successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/api/insertFinalGrade";
    }

    @GetMapping("/studentSuccessRates")
    public String getStudentSuccessRates(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            return "redirect:/api/loginteacher";
        }

        double successRate = statisticsService.calculateSuccessRate();
        model.addAttribute("successRate", successRate);
        return "student_success_rates";
    }
}
