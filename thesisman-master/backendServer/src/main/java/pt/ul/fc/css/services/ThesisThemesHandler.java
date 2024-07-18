package pt.ul.fc.css.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.Master;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.ThesisTheme;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.ThesisThemeRepository;

@Service
public class ThesisThemesHandler {

  private final ThesisThemeRepository thesisThemeRepository;
  private final StudentRepository studentRepository;

  @Autowired
  public ThesisThemesHandler(
      ThesisThemeRepository thesisThemeRepository, StudentRepository studentRepository) {
    this.thesisThemeRepository = thesisThemeRepository;
    this.studentRepository = studentRepository;
  }

  public List<ThesisThemeDTO> getAllThesisThemes() {
    return thesisThemeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
  }

  private ThesisThemeDTO toDTO(ThesisTheme thesisTheme) {
    ThesisThemeDTO dto = new ThesisThemeDTO();
    dto.setId(thesisTheme.getId());
    dto.setTitle(thesisTheme.getTitle());
    dto.setDescription(thesisTheme.getDescription());
    dto.setMonthlyPay(thesisTheme.getMonthlyPay());
    dto.setCreatorName(thesisTheme.getCreator().getUsername());
    dto.setInterestedStudentIds(
        thesisTheme.getInterestedStudents().stream()
            .map(student -> student.getId())
            .collect(Collectors.toList()));
    dto.setMasterIds(
        thesisTheme.getMasters().stream()
            .map(master -> master.getId())
            .collect(Collectors.toList()));
    return dto;
  }

  public List<ThesisThemeDTO> getCompatibleThesisThemes(Long userId) {
    Optional<Student> studentOptional = studentRepository.findById(userId);

    if (studentOptional.isEmpty()) {
      System.out.println("No student found with user ID: " + userId);
      return null;
    }

    Student student = studentOptional.get();
    Master master = student.getMaster();

    if (master == null) {
      System.out.println("No master associated with the student: " + student.getName());
      return null;
    }

    System.out.println(
        "\n\n\n\nMaster ID for student " + student.getName() + ": " + master.getId());

    List<ThesisThemeDTO> allThesisThemes = getAllThesisThemes();
    List<ThesisThemeDTO> compatibleThesisThemes =
        allThesisThemes.stream()
            .filter(
                theme -> {
                  boolean isCompatible = theme.getMasterIds().contains(master.getId());
                  boolean isNotAlreadySelected =
                      student.getStudentThesisThemes().stream()
                          .noneMatch(thesisTheme -> thesisTheme.getId().equals(theme.getId()));
                  System.out.println("Testing thesis theme: " + theme.getTitle());
                  System.out.println("Master IDs for this theme: " + theme.getMasterIds());
                  System.out.println("Does this theme match the student's master? " + isCompatible);
                  System.out.println(
                      "Is this theme not already selected by the student? " + isNotAlreadySelected);
                  return isCompatible && isNotAlreadySelected;
                })
            .collect(Collectors.toList());

    System.out.println(
        "Number of compatible thesis themes found: " + compatibleThesisThemes.size() + "\n\n\n");
    return compatibleThesisThemes;
  }

  public List<ThesisThemeDTO> getUserThesisThemes(Long userId) {
    Optional<Student> studentOptional = studentRepository.findById(userId);

    if (studentOptional.isEmpty()) {
      System.out.println("No student found with user ID: " + userId);
      return null;
    }

    Student student = studentOptional.get();
    List<ThesisTheme> thesisThemesStudent = student.getStudentThesisThemes();

    System.out.println("Thesis themes for student " + student.getName() + ":");

    List<ThesisThemeDTO> thesisThemeDTOs =
        thesisThemesStudent.stream().map(this::toDTO).collect(Collectors.toList());

    thesisThemeDTOs.forEach(dto -> System.out.println("Thesis Theme DTO: " + dto.getTitle()));

    return thesisThemeDTOs;
  }

  public boolean checkThesisThemeSubmission(Long userId, Long thesisThemeId) {
    Optional<Student> studentOptional = studentRepository.findById(userId);

    if (studentOptional.isEmpty()) {
      System.out.println("No student found with user ID: " + userId);
      return false;
    }

    Student student = studentOptional.get();
    ThesisTheme thesisTheme = thesisThemeRepository.getThesisThemeById(thesisThemeId);
    if (thesisTheme == null) return false;

    if (student.getStudentThesisThemes().size() >= 5) {
      return false;
    }
    student.addThesisTheme(thesisTheme);
    thesisTheme.getInterestedStudents().add(student);
    thesisThemeRepository.save(thesisTheme);
    studentRepository.save(student);
    return true;
  }

  public boolean deleteThesisThemeFromUser(Long userId, Long thesisThemeId) {
    Optional<Student> studentOptional = studentRepository.findById(userId);

    if (studentOptional.isEmpty()) {
      System.out.println("No student found with user ID: " + userId);
      return false;
    }

    Student student = studentOptional.get();
    ThesisTheme thesisTheme = thesisThemeRepository.getThesisThemeById(thesisThemeId);
    if (thesisTheme == null) return false;

    // student contains the thesistheme and thesistheme contaisn the student
    if (student.getStudentThesisThemes().contains(thesisTheme)
        && thesisTheme.getInterestedStudents().contains(student)) {
      student.getStudentThesisThemes().remove(thesisTheme);
      thesisTheme.getInterestedStudents().remove(student);
      thesisThemeRepository.save(thesisTheme);
      studentRepository.save(student);
      return true;
    }

    return false;
  }
}
