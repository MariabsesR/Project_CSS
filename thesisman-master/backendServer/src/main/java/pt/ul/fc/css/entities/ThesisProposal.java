package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class ThesisProposal {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(targetEntity = Document.class)
  @JoinColumn(name = "last_document_id", referencedColumnName = "id")
  private Document lastDocumentSubmited;

  @OneToOne(targetEntity = ThesisDefence.class)
  @JoinColumn(name = "thesis_defence_id", referencedColumnName = "id")
  private ThesisDefence thesisDefence;

  public Long getId() {
    return id;
  }

  public ThesisProposal(ThesisDefence thesisDefence, Document lasDocument) {

    this.thesisDefence = thesisDefence;
  }

  public ThesisProposal() {}

  public Document getLastDocumentSubmited() {
    return lastDocumentSubmited;
  }

  public void setLastDocumentSubmited(Document lastDocumentSubmited) {
    this.lastDocumentSubmited = lastDocumentSubmited;
  }

  public ThesisDefence getThesisDefence() {
    return thesisDefence;
  }

  public void setThesisDefence(ThesisDefence thesisDefence) {
    this.thesisDefence = thesisDefence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ThesisProposal)) return false;
    ThesisProposal that = (ThesisProposal) o;
    return Objects.equals(id, that.id)
        && Objects.equals(lastDocumentSubmited, that.lastDocumentSubmited)
        && Objects.equals(thesisDefence, that.thesisDefence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lastDocumentSubmited, thesisDefence);
  }

  @Override
  public String toString() {
    return "ThesisProposal{"
        + "id="
        + id
        + ", lastDocumentSubmited="
        + lastDocumentSubmited
        + ", thesisDefence="
        + thesisDefence
        + '}';
  }
}
