package pt.ul.fc.di.css.javafxexample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThesisTheme {
  // tenho de tirar id daqui
  protected Long id;

  private String title;

  private String description;

  private double monthlyPay;

  private String creator;

  private List<String> masters;

  public ThesisTheme() {

    this.masters = new ArrayList<>();
  }

  public ThesisTheme(String title, String description, double monthlyPay, String creator) {
    this.title = title;
    this.description = description;
    this.monthlyPay = monthlyPay;
    this.creator = creator;

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

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public List<String> getMasters() {
    return masters;
  }

  public void setMasters(List<String> masters) {
    this.masters = masters;
  }

  public void addMaster(String master) {
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
        && Objects.equals(masters, that.masters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, monthlyPay, creator, masters);
  }

  public void setId(long long1) {
    id = long1;
  }

  public String toString() {
    return title + ": " + description + " (Monthly Pay: " + monthlyPay + ")";
  }
}
