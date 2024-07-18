package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.ThesisTheme;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.services.StudentService;
import pt.ul.fc.css.services.TeacherService;
import pt.ul.fc.css.services.ThesisThemeDTO;
import pt.ul.fc.css.services.ThesisThemeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ThesisThemeService thesisThemeService;
    
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/assign")
    public String getAssignPage(Model model, HttpSession session, @RequestParam(required = false) Long selectedStudentId) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null || !teacherRepository.existsById(userId) || !teacherService.isAdmin(userId)) {
            // Handle the case where the userId is not found in the session or is not an admin
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
        
        // Fetch list of students and thesis themes from the database
        List<Student> students = studentService.getAllStudents();
        List<ThesisThemeDTO> themes = thesisThemeService.getThesisThemes();
        
        // Add fetched data to the model
        model.addAttribute("students", students);
        
        // If a student is selected, set the selected student's ID
        if (selectedStudentId != null) {
            // For each theme, check if it's applied by the selected student and add the information to the model
            for (ThesisThemeDTO theme : themes) {
                boolean applied = thesisThemeService.isThemeAppliedByStudent(theme.getId(), selectedStudentId);
                theme.setApplied(applied);
            }
        }
        
        model.addAttribute("themes", themes);

        // Return Thymeleaf template name
        return "assign_thesis";
    }




    @PostMapping("/assign")
    public String assignThemes(@RequestParam Long studentId, 
                               @RequestParam List<Long> themeIds, 
                               RedirectAttributes redirectAttributes) {
        try {
            thesisThemeService.assignForThesisThemes(studentId, themeIds);
            redirectAttributes.addFlashAttribute("message", "Applied successfully for the theme.");
        } catch (IllegalArgumentException e) {
            // Handle validation errors, e.g., no student selected or too many themes selected
        	redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/api/assign";
    }
    
    

}
