package pt.ul.fc.css.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  @Column(nullable = false)
  protected String username;

  @Column(nullable = false)
  protected String password;

  public User() {}

  public User(String username, String password) {

    this.username = username;
    this.password = password;
  }

  /** @return the id */
  public Long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, password, username);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof User)) return false;
    User other = (User) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(password, other.password)
        && Objects.equals(username, other.username);
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
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
}
