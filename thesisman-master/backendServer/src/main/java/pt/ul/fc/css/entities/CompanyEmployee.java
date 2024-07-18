package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "company_employees")
public class CompanyEmployee extends Responsible {

  private String name;

  @OneToMany(targetEntity = Project.class)
  private List<Project> projects;

  @OneToMany(targetEntity = ThesisTheme.class)
  private List<ThesisTheme> thesisThemes;

  public CompanyEmployee() {
    this.projects = new ArrayList<>();
    this.thesisThemes = new ArrayList<>();
  }

  public CompanyEmployee(String username, String password, String name) {
    super(username, password);
    this.name = name;
    this.projects = new ArrayList<>();
    this.thesisThemes = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** @return the username */
  public String getUsername() {
    return username;
  }

  /** @param username the username to set */
  public void setUsername(String username) {
    this.username = username;
  }

  /** @return the password */
  public String getPassword() {
    return password;
  }

  /** @param password the password to set */
  public void setPassword(String password) {
    this.password = password;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public void addProject(Project project) {
    this.projects.add(project);
  }

  public List<ThesisTheme> getThesisThemes() {
    return thesisThemes;
  }

  public void setThesisThemes(List<ThesisTheme> thesisThemes) {
    this.thesisThemes = thesisThemes;
  }

  public void addThesisTheme(ThesisTheme thesisThemes) {
    this.thesisThemes.add(thesisThemes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    CompanyEmployee that = (CompanyEmployee) o;
    return Objects.equals(name, that.name)
        && Objects.equals(projects, that.projects)
        && Objects.equals(thesisThemes, that.thesisThemes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, projects, thesisThemes);
  }

  @Override
  public String toString() {
    return "CompanyEmployee{"
        + "name='"
        + name
        + '\''
        + ", projects="
        + projects
        + ", thesisThemes="
        + thesisThemes
        + '}';
  }
}
