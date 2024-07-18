package pt.ul.fc.css.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ThesisTheme {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private double monthlyPay;

  @ManyToOne(targetEntity = Responsible.class)
  @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
  @JsonIgnoreProperties("thesisThemes") // Prevent recursive serialization
  private Responsible creator;

  @ManyToMany(targetEntity = Student.class)
  @JsonIgnoreProperties("thesisThemes") // Prevent recursive serialization
  private List<Student> interestedStudents;

  @ManyToMany(targetEntity = Master.class)
  private List<Master> masters;

  public ThesisTheme() {
    this.interestedStudents = new ArrayList<>();
    this.masters = new ArrayList<>();
  }

  public ThesisTheme(String title, String description, double monthlyPay, Responsible creator) {
    this.title = title;
    this.description = description;
    this.monthlyPay = monthlyPay;
    this.creator = creator;
    this.interestedStudents = new ArrayList<>();
    this.masters = new ArrayList<>();
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getMonthlyPay() {
    return monthlyPay;
  }

  public void setMonthlyPay(double monthlyPay) {
    this.monthlyPay = monthlyPay;
  }

  public Responsible getCreator() {
    return creator;
  }

  public void setCreator(Responsible creator) {
    this.creator = creator;
  }

  public List<Student> getInterestedStudents() {
    return interestedStudents;
  }

  public void setInterestedStudents(List<Student> interestedStudents) {
    this.interestedStudents = interestedStudents;
  }

  public List<Master> getMasters() {
    return masters;
  }

  public void setMasters(List<Master> masters) {
    this.masters = masters;
  }

  public void addMaster(Master master) {
    this.masters.add(master);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ThesisTheme)) return false;
    ThesisTheme that = (ThesisTheme) o;
    return Double.compare(that.monthlyPay, monthlyPay) == 0
        && Objects.equals(id, that.id)
        && Objects.equals(title, that.title)
        && Objects.equals(description, that.description)
        && Objects.equals(creator, that.creator)
        && Objects.equals(interestedStudents, that.interestedStudents)
        && Objects.equals(masters, that.masters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, monthlyPay, creator, interestedStudents, masters);
  }
}
