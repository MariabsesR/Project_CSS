package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class TimeSlot {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Long getId() {
    return id;
  }

  @Column(nullable = false)
  private LocalTime startTime;

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  @Column(nullable = false)
  private LocalTime endTime;

  @Column(nullable = false)
  private LocalDate day;

  public TimeSlot(LocalTime startTime, LocalTime endTime, LocalDate day) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.day = day;
  }

  public TimeSlot() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TimeSlot)) return false;
    TimeSlot timeSlot = (TimeSlot) o;
    return Objects.equals(id, timeSlot.id)
        && Objects.equals(startTime, timeSlot.startTime)
        && Objects.equals(endTime, timeSlot.endTime)
        && Objects.equals(day, timeSlot.day);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, startTime, endTime, day);
  }

  public LocalDate getDay() {
    return day;
  }

  @Override
  public String toString() {
    return "TimeSlot{"
        + "id="
        + id
        + ", startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", day="
        + day
        + '}';
  }

  public void setDay(LocalDate day) {
    this.day = day;
  }
}
