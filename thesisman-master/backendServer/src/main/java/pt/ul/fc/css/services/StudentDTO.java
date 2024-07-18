package pt.ul.fc.css.services;

import java.util.List;
import java.util.Objects;
import pt.ul.fc.css.entities.ThesisTheme;

public class StudentDTO {

  private String name;
  private Long id;
  private double averageGrade;
  // a student can have multiple thesis themes on the add function there needs to be averification
  // of the limit

  private List<ThesisTheme> studentThesisThemes;

  public List<ThesisTheme> getStudentThesisThemes() {
    return studentThesisThemes;
  }
  /** @param studentThesisThemes the studentThesisThemes to set */
  public void setStudentThesisThemes(List<ThesisTheme> studentThesisThemes) {
    this.studentThesisThemes = studentThesisThemes;
  }
  /** @param averageGrade the averageGrade to set */
  public void setAverageGrade(double averageGrade) {
    this.averageGrade = averageGrade;
  }

  public StudentDTO(String name, double averageGrade, List<ThesisTheme> studentThesisThemes) {
    this.name = name;
    this.averageGrade = averageGrade;
    this.studentThesisThemes = studentThesisThemes;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the averageGrade */
  public double getAverageGrade() {
    return averageGrade;
  }

  /** @param averageGrade the averageGrade to set */
  public void setAverageGrade(Long averageGrade) {
    this.averageGrade = averageGrade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    StudentDTO student = (StudentDTO) o;
    return Double.compare(averageGrade, student.averageGrade) == 0
        && Objects.equals(name, student.name)
        && Objects.equals(studentThesisThemes, student.studentThesisThemes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, averageGrade, studentThesisThemes);
  }

  @Override
  public String toString() {
    return "Student{"
        + "name='"
        + name
        + '\''
        + ", averageGrade="
        + averageGrade
        + ", studentThesisThemes="
        + studentThesisThemes
        + '}';
  }

  public void addThesisTheme(ThesisTheme thesisTheme) {

    studentThesisThemes.add(thesisTheme);
  }

  public void removeThesisTheme(ThesisTheme thesisTheme) {

    studentThesisThemes.remove(thesisTheme);
  }
  
  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }
}
