package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(targetEntity = TimeSlot.class)
  private List<TimeSlot> availableTimeSlots;

  public Room() {
    this.availableTimeSlots = new ArrayList<>();
  }

  public Room(List<TimeSlot> availableTimeSlots) {
    this.availableTimeSlots = availableTimeSlots;
  }

  public void addAvailableTimeSlot(TimeSlot timeSlot) {
    this.availableTimeSlots.add(timeSlot);
  }

  @Override
  public String toString() {
    return "Room{" + "id=" + id + ", availableTimeSlots=" + availableTimeSlots + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false; // Explicitly specify the type
    Room room = (Room) o; // Separate variable declaration
    return Objects.equals(id, room.id)
        && Objects.equals(availableTimeSlots, room.availableTimeSlots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, availableTimeSlots);
  }

  public Long getId() {
    return this.id;
  }
}
