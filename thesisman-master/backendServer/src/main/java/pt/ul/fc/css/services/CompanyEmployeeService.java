package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.services.CompanyEmployeeDTO;
import pt.ul.fc.css.entities.CompanyEmployee;
import pt.ul.fc.css.entities.Project;
import pt.ul.fc.css.entities.ThesisTheme;
import pt.ul.fc.css.repositories.CompanyEmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyEmployeeService {

    @Autowired
    private CompanyEmployeeRepository companyEmployeeRepository;

    @Transactional
    public CompanyEmployeeDTO createCompanyEmployee(String username, String password, String name) {
        // Check if a company employee with the given username already exists
        if (companyEmployeeRepository.findOptionalByUsername(username).isPresent()) {
            throw new IllegalArgumentException("A company employee with this username already exists");
        }

        // Create a new company employee
        CompanyEmployee companyEmployee = new CompanyEmployee(username, password, name);
        companyEmployee = companyEmployeeRepository.save(companyEmployee);
        return dtofy(companyEmployee);
    }

    public Optional<CompanyEmployeeDTO> getCompanyEmployee(Long id) {
        return companyEmployeeRepository.findById(id).map(CompanyEmployeeService::dtofy);
    }

    public List<CompanyEmployeeDTO> getCompanyEmployees() {
        List<CompanyEmployee> companyEmployees = companyEmployeeRepository.findAll();
        return companyEmployees.stream().map(CompanyEmployeeService::dtofy).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCompanyEmployee(Long id) {
        companyEmployeeRepository.findById(id).ifPresent(companyEmployeeRepository::delete);
    }
    
    public CompanyEmployeeDTO login(String username, String password) {
        // Retrieve company employee from the database by username and password
        Optional<CompanyEmployee> companyEmployee = companyEmployeeRepository.findByUsernameAndPassword(username, password);

        // If company employee is found, convert it to CompanyEmployeeDTO and return
        if (companyEmployee.isPresent()) {
            return dtofy(companyEmployee.get());
        }

        // If company employee is not found, return null or throw an exception
        return null;
    }

    private static CompanyEmployeeDTO dtofy(CompanyEmployee companyEmployee) {
        CompanyEmployeeDTO dto = new CompanyEmployeeDTO();
        dto.setId(companyEmployee.getId());
        dto.setUsername(companyEmployee.getUsername());
        dto.setName(companyEmployee.getName());
        dto.setProjectIds(companyEmployee.getProjects().stream().map(Project::getId).collect(Collectors.toList()));
        dto.setThesisThemeIds(companyEmployee.getThesisThemes().stream().map(ThesisTheme::getId).collect(Collectors.toList()));
        return dto;
    }
}
