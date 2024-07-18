package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "masters")
public class Master {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private Long code;

  @OneToMany(targetEntity = Student.class)
  private List<Student> students;

  public Master() {
    this.students = new ArrayList<>();
  }

  public Master(String name, Long code) {
    this.name = name;
    this.code = code;
    this.students = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public void addStudent(Student student) {
    this.students.add(student);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Master master = (Master) o;
    return Objects.equals(id, master.id)
        && Objects.equals(name, master.name)
        && Objects.equals(code, master.code)
        && Objects.equals(students, master.students);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, students);
  }

  @Override
  public String toString() {
    return "Master{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", code="
        + code
        + ", students="
        + students
        + '}';
  }
}
