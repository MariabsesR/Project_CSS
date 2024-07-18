package pt.ul.fc.css.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Teacher extends Responsible {

  @Column(nullable = false)
  private String name;

  @ManyToMany(targetEntity = Thesis.class)
  private List<Thesis> theses;
  
  @Column(nullable = false)
  private boolean isAdmin;

  public Teacher() {
    this.theses = new ArrayList<>();
    this.isAdmin = false;
  }

  public Teacher(String name, String username, String password, boolean isAdmin) {
    super(username, password);
    this.name = name;
    this.theses = new ArrayList<>();
    this.isAdmin = isAdmin;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  public List<Thesis> getTheses() {
    return theses;
  }

  public void setTheses(List<Thesis> theses) {
    this.theses = theses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Teacher teacher = (Teacher) o;
    return Objects.equals(name, teacher.name) && Objects.equals(theses, teacher.theses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, theses);
  }

  @Override
  public String toString() {
    return "Teacher{" + "name='" + name + '\'' + ", theses=" + theses + '}';
  }
  
  public boolean isAdmin() {
      return isAdmin;
  }

  public void setAdmin(boolean admin) {
      isAdmin = admin;
  }
}
