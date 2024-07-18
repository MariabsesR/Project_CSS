package pt.ul.fc.css;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.repositories.CompanyEmployeeRepository;
import pt.ul.fc.css.services.CompanyEmployeeDTO;
import pt.ul.fc.css.services.CompanyEmployeeService;

@Controller
@RequestMapping("/api")
public class CompanyEmployeeController {

    @Autowired
    private CompanyEmployeeService companyEmployeeService;
    
    @Autowired
    private CompanyEmployeeRepository companyRepository;

    @GetMapping("/register-company-employee")
    public String showEmployeeRegistrationForm() {
        return "company_employee_add"; // This should match the name of your Thymeleaf template (without the ".html" extension)
    }

    @PostMapping("/companyemployee")
    public String postCompany(CompanyEmployeeDTO companyEmployeeDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            CompanyEmployeeDTO createdCompanyEmployee = companyEmployeeService.createCompanyEmployee(
                    companyEmployeeDTO.getUsername(), companyEmployeeDTO.getPassword(), companyEmployeeDTO.getName());
            session.setAttribute("userId", createdCompanyEmployee.getId());
            redirectAttributes.addFlashAttribute("message", "Company employee created successfully.");
            return "redirect:/api/dashboard-company";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/api/register-company-employee"; // Adjust this to your registration page URL
        }
    }

    @GetMapping("/logincompany")
    public String showCompanyLoginForm() {
        return "login_company"; // This should match the name of your Thymeleaf template without the ".html" extension
    }

    @PostMapping("/logincompany")
    public String handleCompanyLogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        CompanyEmployeeDTO employee = companyEmployeeService.login(username, password);
        if (employee != null) {
            session.setAttribute("userId", employee.getId());
            // Redirect to a company dashboard or home page after successful login
            return "redirect:/api/dashboard-company";
        } else {
            // Return to login page with an error message if authentication fails
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/api/logincompany";
        }
    }

    @GetMapping("/dashboard-company")
    public String showCompanyDashboard(HttpSession session) {
    	Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null || !companyRepository.existsById(userId)) {
            // Handle the case where the userId is not found in the session or is not a student
            return "redirect:/api/logincompany"; // Or any appropriate error handling
        }
        return "dashboard_company"; // This should match the name of your Thymeleaf template for the company dashboard
    }
}
