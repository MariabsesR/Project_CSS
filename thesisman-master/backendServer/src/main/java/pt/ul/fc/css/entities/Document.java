package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String filePath; // Path to the file instead of the File object

  public Document(String filePath) {
    this.filePath = filePath;
  }

  public Document() {}

  public String getFilePath() {
    return filePath;
  }

  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }


  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Document)) return false;
    Document document = (Document) o;
    return Objects.equals(id, document.id) && Objects.equals(filePath, document.filePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, filePath);
  }

  @Override
  public String toString() {
    return "Document{" + "id=" + id + ", filePath='" + filePath + '\'' + '}';
  }
}
