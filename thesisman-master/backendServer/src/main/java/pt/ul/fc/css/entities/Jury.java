package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "jury")
public class Jury {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(targetEntity = ThesisDefence.class)
  private ThesisDefence thesisDefences;

  @OneToMany(targetEntity = Teacher.class)
  private List<Teacher> teachers;

  public Jury() {
    this.teachers = new ArrayList<>();
  }

  public Jury(ThesisDefence thesisDefences) {
    this.thesisDefences = thesisDefences;
    this.teachers = new ArrayList<>();
  }

  public ThesisDefence getThesisDefences() {
    return thesisDefences;
  }

  public void setThesisDefences(ThesisDefence thesisDefences) {
    this.thesisDefences = thesisDefences;
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
  }

  public void addTeacher(Teacher teacher) {
    this.teachers.add(teacher);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Jury jury = (Jury) o;
    return Objects.equals(id, jury.id)
        && Objects.equals(thesisDefences, jury.thesisDefences)
        && Objects.equals(teachers, jury.teachers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, thesisDefences, teachers);
  }

  @Override
  public String toString() {
    return "Jury{"
        + "id="
        + id
        + ", thesisDefences="
        + thesisDefences
        + ", teachers="
        + teachers
        + '}';
  }
}
