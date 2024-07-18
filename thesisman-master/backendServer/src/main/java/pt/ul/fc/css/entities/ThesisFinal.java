package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class ThesisFinal {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(targetEntity = Document.class)
  @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
  private Document document;

  @OneToOne(targetEntity = ThesisDefence.class)
  @JoinColumn(name = "thesis_defence_id", referencedColumnName = "id")
  private ThesisDefence thesisDefence;

  public ThesisFinal(Document document, ThesisDefence thesisDefence) {
    this.document = document;
    this.thesisDefence = thesisDefence;
  }

  public ThesisFinal() {}

  public Long getId() {
    return id;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ThesisFinal)) return false;
    ThesisFinal that = (ThesisFinal) o;
    return Objects.equals(id, that.id) && Objects.equals(document, that.document);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document);
  }

  @Override
  public String toString() {
    return "ThesisFinal{" + "id=" + id + ", document=" + document + '}';
  }
}
