package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "thesis")
public class Thesis {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  protected String title;

  @OneToOne(targetEntity = Student.class)
  protected Student student;

  @OneToOne(targetEntity = ThesisTheme.class)
  protected ThesisTheme thesisTheme;

  @OneToOne(targetEntity = ThesisFinal.class)
  protected ThesisFinal thesisFinal;

  @OneToMany(targetEntity = ThesisProposal.class)
  protected List<ThesisProposal> thesisProposal;

  @ManyToMany(targetEntity = Teacher.class)
  protected List<Teacher> teachers;

  public Thesis() {
    this.thesisProposal = new ArrayList<>();
    this.teachers = new ArrayList<>();
  }

  public Thesis(String title, Student student, ThesisTheme thesisTheme, ThesisFinal thesisFinal) {
    this.title = title;
    this.student = student;
    this.thesisTheme = thesisTheme;
    this.thesisFinal = thesisFinal;
    this.thesisProposal = new ArrayList<>();
    this.teachers = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public ThesisTheme getThesisTheme() {
    return thesisTheme;
  }

  public void setThesisTheme(ThesisTheme thesisTheme) {
    this.thesisTheme = thesisTheme;
  }

  public ThesisFinal getThesisFinal() {
    return thesisFinal;
  }

  public void setThesisFinal(ThesisFinal thesisFinal) {
    this.thesisFinal = thesisFinal;
  }

  public List<ThesisProposal> getThesisProposal() {
    return thesisProposal;
  }

  public void setThesisProposal(List<ThesisProposal> thesisProposal) {
    this.thesisProposal = thesisProposal;
  }

  public void addThesisProposal(ThesisProposal thesisProposal) {
    this.thesisProposal.add(thesisProposal);
  }

  public List<Teacher> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
  }

  public void addTeacher(Teacher teacher) {
    this.teachers.add(teacher);
    teacher.getTheses().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Thesis thesis = (Thesis) o;
    return Objects.equals(id, thesis.id)
        && Objects.equals(title, thesis.title)
        && Objects.equals(student, thesis.student)
        && Objects.equals(thesisTheme, thesis.thesisTheme)
        && Objects.equals(thesisFinal, thesis.thesisFinal)
        && Objects.equals(thesisProposal, thesis.thesisProposal)
        && Objects.equals(teachers, thesis.teachers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, student, thesisTheme, thesisFinal, thesisProposal, teachers);
  }

  @Override
  public String toString() {
    return "Thesis{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", student="
        + student
        + ", thesisTheme="
        + thesisTheme
        + ", thesisFinal="
        + thesisFinal
        + ", thesisProposal="
        + thesisProposal
        + ", teachers="
        + teachers
        + '}';
  }
}
