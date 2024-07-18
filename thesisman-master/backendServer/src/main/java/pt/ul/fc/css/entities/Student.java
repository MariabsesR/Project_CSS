package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Student extends User {

  @Column(nullable = false)
  private String name;

  // @OneToOne(targetEntity = Student.class)
  // private Thesis thesis;

  private double averageGrade;
  // a student can have multiple thesis themes on the add function there needs to be averification
  // of the limit
  @ManyToMany(targetEntity = ThesisTheme.class)
  private List<ThesisTheme> studentThesisThemes;

  @ManyToOne
  @JoinColumn(name = "master_id")
  private Master master;
  /** @return the studentThesisThemes */
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
  /** @return the master */
  public Student() {
    studentThesisThemes = new ArrayList<>();
  }

  public Student(
      String username, String password, String name, double averageGrade, Master master) {
    super(username, password);
    this.name = name;
    this.averageGrade = averageGrade;
    this.studentThesisThemes = new ArrayList<>();
    this.master = master;
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
    Student student = (Student) o;
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

  public Master getMaster() {
    return master;
  }

  public void setMaster(Master master) {
    this.master = master;
  }
}
