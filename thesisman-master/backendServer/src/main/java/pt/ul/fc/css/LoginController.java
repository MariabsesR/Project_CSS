package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.services.LoginService;
import pt.ul.fc.css.services.StudentDTO;
import pt.ul.fc.css.services.TeacherDTO;

@Controller
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    
    @GetMapping("/loginteacher")
    public String showLoginTeacherForm() {
        return "login_teacher"; // This should match the name of your Thymeleaf template without the ".html" extension
    }
    
    @PostMapping("/loginteacher")
    public String handleLoginTeacher(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        // Call the login service to handle the login logic
        TeacherDTO teacherDTO = loginService.loginTeacher(username);

        // Check if teacherDTO is not null, indicating successful login
        if (teacherDTO != null) {
            session.setAttribute("userId", teacherDTO.getId());
            // Redirect to a welcome page or dashboard after successful login
            return "redirect:/api/dashboard-teacher";
        } else {
            // If login fails, redirect back to the login page with an error message
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/api/loginteacher";
        }
    }

    @GetMapping("/loginuni")
    public String showLoginForm() {
        return "login_uni"; // This should match the name of your Thymeleaf template without the ".html" extension
    }

    @PostMapping("/loginuni")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        // Call the login service to handle the login logic
        StudentDTO studentDTO = loginService.login(username);

        // Check if studentDTO is not null, indicating successful login
        if (studentDTO != null) {
            session.setAttribute("userId", studentDTO.getId());
            // Redirect to a welcome page or dashboard after successful login
            return "redirect:/api/dashboard";
        } else {
            // If login fails, redirect back to the login page with an error message
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/api/loginuni";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !studentRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginuni"; // Or any appropriate error handling
        }
        return "dashboard"; // This should match the name of your Thymeleaf template for the dashboard
    }
    
    @GetMapping("/dashboard-teacher")
    public String showDashboardTeacher(HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !teacherRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/loginteacher"; // Or any appropriate error handling
        }
        return "dashboard_teacher"; // This should match the name of your Thymeleaf template for the dashboard
    }
}
