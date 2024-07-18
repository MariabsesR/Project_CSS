package pt.ul.fc.css.services;

import java.util.List;

public class ThesisThemeDTO {
  private Long id;
  private String title;
  private String description;
  private double monthlyPay;
  private String creatorName;
  private List<Long> interestedStudentIds;
  private List<Long> masterIds;
  private List<String> masterNames;
  private boolean applied;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public List<Long> getInterestedStudentIds() {
    return interestedStudentIds;
  }

  public void setInterestedStudentIds(List<Long> interestedStudentIds) {
    this.interestedStudentIds = interestedStudentIds;
  }

  public List<Long> getMasterIds() {
    return masterIds;
  }

  public void setMasterIds(List<Long> masterIds) {
    this.masterIds = masterIds;
  }

  public List<String> getMasterNames() {
    return masterNames;
  }

  public void setMasterNames(List<String> masterNames) {
    this.masterNames = masterNames;
  }
  
  public boolean isApplied() {
      return applied;
  }

  public void setApplied(boolean applied) {
      this.applied = applied;
  }
}
