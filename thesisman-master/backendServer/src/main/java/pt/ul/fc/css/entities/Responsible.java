package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "responsible_type", discriminatorType = DiscriminatorType.STRING)
public class Responsible extends User {

  @OneToMany(targetEntity = ThesisTheme.class)
  protected List<ThesisTheme> thesisThemes;

  public Responsible(String username, String password) {
    super(username, password);
    this.thesisThemes = new ArrayList<>();
  }

  public Responsible() {
    this.thesisThemes = new ArrayList<>();
  }

  public List<ThesisTheme> getThesisThemes() {
    return thesisThemes;
  }

  public void setThesisThemes(List<ThesisTheme> thesisThemes) {
    this.thesisThemes = thesisThemes;
  }

  public void addThesisThemes(ThesisTheme thesisTheme) {
    thesisThemes.add(thesisTheme);
  }

  public void removeThesisThemes(ThesisTheme thesisTheme) {
    thesisThemes.remove(thesisTheme);
  }
}
