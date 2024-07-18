package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.repositories.CompanyEmployeeRepository;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.services.MasterDTO;
import pt.ul.fc.css.services.MasterService;
import pt.ul.fc.css.services.ThesisThemeDTO;
import pt.ul.fc.css.services.ThesisThemeService;

import java.util.List;

@Controller
@RequestMapping("/api")
public class WebThesisThemeController {

    @Autowired
    private ThesisThemeService thesisThemeService;

    @Autowired
    private MasterService masterService;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CompanyEmployeeRepository companyRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/submit-theme-teacher")
    public String showThemeSubmissionForm(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
        List<MasterDTO> availableMasters = masterService.getAllMasters();
        model.addAttribute("availableMasters", availableMasters);
        model.addAttribute("thesisTheme", new ThesisThemeDTO());
        return "submit_theme_teacher"; // This should match the name of your Thymeleaf template without the ".html" extension
    }

    @PostMapping("/submit-theme-teacher")
    public String handleThemeSubmission(@RequestParam String title,
                                        @RequestParam String description,
                                        @RequestParam double monthlyPay,
                                        @RequestParam String creatorUsername,
                                        @RequestParam List<Long> masterIds,
                                        RedirectAttributes redirectAttributes) {
        try {
            ThesisThemeDTO thesisTheme = thesisThemeService.createThesisThemeForResponsible(title, description, monthlyPay, creatorUsername, masterIds);
            redirectAttributes.addFlashAttribute("message", "Theme submitted successfully");
            return "redirect:/api/themes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/submit-theme-teacher";
        }
    }

    @GetMapping("/themes")
    public String showThemes(Model model) {
        List<ThesisThemeDTO> themes = thesisThemeService.getThesisThemes();
        model.addAttribute("themes", themes);
        return "themes"; // This should match the name of your Thymeleaf template to display themes
    }

    @GetMapping("/submit-theme-company")
    public String showThemeSubmissionFormCompany(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !companyRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/logincompany"; // Or any appropriate error handling
        }
        List<MasterDTO> availableMasters = masterService.getAllMasters();
        model.addAttribute("availableMasters", availableMasters);
        model.addAttribute("thesisTheme", new ThesisThemeDTO());
        return "submit_theme_company";
    }

    @PostMapping("/submit-theme-company")
    public String submitCompanyThesisTheme(@RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam double monthlyPay,
                                           @RequestParam String creatorUsername,
                                           @RequestParam List<Long> masterIds,
                                           RedirectAttributes redirectAttributes) {
        try {
            ThesisThemeDTO theme = thesisThemeService.createThesisThemeForCompanyEmployee(title, description, monthlyPay, creatorUsername, masterIds);
            redirectAttributes.addFlashAttribute("message", "Thesis theme submitted successfully!");
            return "redirect:/api/themes"; // Change this to your success page
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/submit-theme-company"; // Change this to your form page
        }
    }
    
    @GetMapping("/apply")
    public String showAvailableThemesForApplication(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !studentRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginuni"; // Or any appropriate error handling
        }
        
        List<ThesisThemeDTO> themes = thesisThemeService.getThesisThemes();
        model.addAttribute("themes", themes);
        model.addAttribute("studentId", userId);
        
        // For each theme, check if it's applied by the student and add the information to the model
        for (ThesisThemeDTO theme : themes) {
            boolean applied = thesisThemeService.isThemeAppliedByStudent(theme.getId(), userId);
            theme.setApplied(applied);
        }
        
        return "apply"; // This should match the name of your Thymeleaf template
    }

    
    @PostMapping("/apply")
    public String applyForTheme(@RequestParam Long studentId,
    							@RequestParam List<Long> themeIds,
                                RedirectAttributes redirectAttributes) {
        try {
            thesisThemeService.applyForThesisThemes(studentId, themeIds);
            redirectAttributes.addFlashAttribute("message", "Applied successfully for the theme.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/api/apply";
    }
    
    @GetMapping("/cancel")
    public String showAppliedThemes(Model model, HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !studentRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginuni"; // Or any appropriate error handling
        }
        List<ThesisThemeDTO> appliedThemes = thesisThemeService.getAppliedThemes(userId);
        model.addAttribute("themes", appliedThemes);
        model.addAttribute("studentId", userId);
        return "cancel";
    }

    
    @PostMapping("/cancel")
    public String cancelApplication(@RequestParam Long studentId, @RequestParam List<Long> themeIds) {
        for (Long themeId : themeIds) {
            thesisThemeService.cancelApplication(studentId, themeId);
        }
        return "redirect:/api/cancel";
    }

}
