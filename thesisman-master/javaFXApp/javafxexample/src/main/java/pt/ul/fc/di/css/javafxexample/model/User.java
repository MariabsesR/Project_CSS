package pt.ul.fc.di.css.javafxexample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

  private long id;
  // DUVIDAAAAAAAAAAAAA acho que nao preciso do id aqui so na base de dados
  private final StringProperty username = new SimpleStringProperty();

  public final StringProperty usernameProperty() {
    return this.username;
  }

  private final StringProperty password = new SimpleStringProperty();

  public final StringProperty passwordProperty() {
    return this.username;
  }

  public StringProperty getUsername() {
    return username;
  }

  public StringProperty getPassword() {
    return password;
  }

  public StringProperty setUsername() {
    return username;
  }

  public final void setUsername(final String userName) {
    this.usernameProperty().set(userName);
  }

  public final void setPassword(final String password) {
    this.passwordProperty().set(password);
  }

  public User(String username, String password, Long id) {
    setUsername(username);
    setPassword(password);
    this.id = id;
  }

  @Override
  public String toString() {
    return "User [ username=" + username + ", password=" + password + "]";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
