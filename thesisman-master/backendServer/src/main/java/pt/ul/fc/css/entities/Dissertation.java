package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dissertations")
public class Dissertation extends Thesis {

  private String name;

  public Dissertation() {}

  public Dissertation(
      String title,
      Student student,
      ThesisTheme thesisTheme,
      ThesisFinal thesisFinal,
      String name) {
    super(title, student, thesisTheme, thesisFinal);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dissertation that = (Dissertation) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Dissertation{" + "name='" + name + '\'' + '}';
  }
}
