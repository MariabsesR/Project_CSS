package pt.ul.fc.css.entities;

import jakarta.persistence.*;

@Entity
public class ThesisDefence {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private boolean isInPerson;

  @Column(nullable = false)
  private int duration;

  @OneToOne(targetEntity = TimeSlot.class)
  @JoinColumn(name = "time_slot_id", referencedColumnName = "id", nullable = false)
  private TimeSlot scheduledTime;

  @OneToOne(targetEntity = Document.class)
  @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
  private Document document;

  @ManyToOne(targetEntity = Room.class) // Many ThesisDefences can be associated with one Room
  private Room room;

  @Column private double grade;

  public ThesisDefence(
      boolean isInPerson, int duration, TimeSlot scheduledTime, Document document) {
    this.isInPerson = isInPerson;
    this.duration = duration;
    this.scheduledTime = scheduledTime;
    this.document = document;
  }

  public Long getId() {
    return id;
  }

  public boolean isInPerson() {
    return isInPerson;
  }

  public void setInPerson(boolean inPerson) {
    isInPerson = inPerson;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public TimeSlot getScheduledTime() {
    return scheduledTime;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  public void setScheduledTime(TimeSlot scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public Room getRoom() {
    if (isInPerson) return room;
    else return null;
  }

  public void setRoom(Room room) {
    if (isInPerson) this.room = room;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public ThesisDefence() {}
}
