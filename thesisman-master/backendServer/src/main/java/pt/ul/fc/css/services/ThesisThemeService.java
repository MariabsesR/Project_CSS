package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.ThesisTheme;
import pt.ul.fc.css.entities.Responsible;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.Teacher;
import pt.ul.fc.css.entities.CompanyEmployee;
import pt.ul.fc.css.entities.Master;
import pt.ul.fc.css.repositories.ThesisThemeRepository;
import pt.ul.fc.css.repositories.ResponsibleRepository;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.repositories.CompanyEmployeeRepository;
import pt.ul.fc.css.repositories.MasterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThesisThemeService {

    @Autowired
    private ThesisThemeRepository thesisThemeRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CompanyEmployeeRepository companyEmployeeRepository;

    @Autowired
    private MasterRepository masterRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    public ThesisThemeDTO createThesisThemeForResponsible(String title, String description, double monthlyPay, String creatorUsername, List<Long> masterIds) {
    	Optional<Teacher> teacherOptional = teacherRepository.findOptionalByUsername(creatorUsername);
        if (teacherOptional.isPresent()) {
            Teacher creator = teacherOptional.get();
            List<Master> masters = masterRepository.findAllById(masterIds);
            ThesisTheme thesisTheme = new ThesisTheme(title, description, monthlyPay, creator);
            thesisTheme.setMasters(masters);
            thesisTheme = thesisThemeRepository.save(thesisTheme);
            return convertToDto(thesisTheme);
        } else {
            throw new IllegalArgumentException("Teacher with username " + creatorUsername + " not found");
        }
    }

    public ThesisThemeDTO createThesisThemeForCompanyEmployee(String title, String description, double monthlyPay, String creatorUsername, List<Long> masterIds) {
        Optional<CompanyEmployee> employeeOptional = companyEmployeeRepository.findOptionalByUsername(creatorUsername);
        if (employeeOptional.isPresent()) {
            CompanyEmployee creator = employeeOptional.get();
            List<Master> masters = masterRepository.findAllById(masterIds);
            ThesisTheme thesisTheme = new ThesisTheme(title, description, monthlyPay, creator);
            thesisTheme.setMasters(masters);
            thesisTheme = thesisThemeRepository.save(thesisTheme);
            return convertToDto(thesisTheme);
        } else {
            throw new IllegalArgumentException("Company Employee with username " + creatorUsername + " not found");
        }
    }



    public List<ThesisThemeDTO> getThesisThemes() {
        return thesisThemeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<ThesisThemeDTO> getAppliedThemes(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getStudentThesisThemes().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }

    
    public boolean isThemeAppliedByStudent(Long themeId, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<ThesisTheme> appliedThemes = student.getStudentThesisThemes();
            return appliedThemes.stream().anyMatch(theme -> theme.getId().equals(themeId));
        } else {
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }
    
    
    public void applyForThesisThemes(Long studentId, List<Long> themeIds) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Check if the student will exceed the limit of 5 themes
            if (student.getStudentThesisThemes().size() + themeIds.size() > 5) {
                throw new IllegalArgumentException("You cannot apply for more than 5 thesis themes.");
            }

            List<ThesisTheme> themesToApply = thesisThemeRepository.findAllById(themeIds);
            for (ThesisTheme theme : themesToApply) {
                if (!student.getStudentThesisThemes().contains(theme)) {
                    student.addThesisTheme(theme);
                }
            }
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }
    
    public void assignForThesisThemes(Long studentId, List<Long> themeIds) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Check if the student will exceed the limit of 5 themes
            if (themeIds.size() > 5) {
                throw new IllegalArgumentException("You cannot apply for more than 5 thesis themes.");
            }

            List<ThesisTheme> themesToApply = thesisThemeRepository.findAllById(themeIds);
            student.setStudentThesisThemes(themesToApply);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }
    
    public void cancelApplication(Long studentId, Long themeId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<ThesisTheme> appliedThemes = student.getStudentThesisThemes();
            Optional<ThesisTheme> themeToRemove = appliedThemes.stream()
                    .filter(theme -> theme.getId().equals(themeId))
                    .findFirst();

            if (themeToRemove.isPresent()) {
                student.removeThesisTheme(themeToRemove.get());
                studentRepository.save(student);
            } else {
                throw new IllegalArgumentException("Theme with ID " + themeId + " is not applied by the student.");
            }
        } else {
            throw new IllegalArgumentException("Invalid student ID.");
        }
    }
    
    public ThesisTheme findById(Long id) {
        Optional<ThesisTheme> thesisThemeOptional = thesisThemeRepository.findById(id);
        return thesisThemeOptional.orElse(null);
    }

    private ThesisThemeDTO convertToDto(ThesisTheme thesisTheme) {
        ThesisThemeDTO dto = new ThesisThemeDTO();
        dto.setId(thesisTheme.getId());
        dto.setTitle(thesisTheme.getTitle());
        dto.setDescription(thesisTheme.getDescription());
        dto.setMonthlyPay(thesisTheme.getMonthlyPay());
        dto.setCreatorName(thesisTheme.getCreator().getUsername());
        dto.setInterestedStudentIds(thesisTheme.getInterestedStudents().stream()
                .map(student -> student.getId())
                .collect(Collectors.toList()));
        dto.setMasterIds(thesisTheme.getMasters().stream()
                .map(master -> master.getId())
                .collect(Collectors.toList()));
        dto.setMasterNames(thesisTheme.getMasters().stream()
                .map(master -> master.getName())
                .collect(Collectors.toList())); // Set master names
        return dto;
    }
}
