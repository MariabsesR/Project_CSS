package pt.ul.fc.di.css.javafxexample.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DataModel {

  /* in this way personList also reports
   * mutations of the elements in it by using the given extractor.
   * Observable objects returned by extractor (applied to each list element) are listened
   * for changes and transformed into "update" change of ListChangeListener.
   * since the phone is not visible, changes in the phone do not need to be propagated
   */

  private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
  private String urlServer;

  public ObjectProperty<User> currentUserProperty() {
    return currentUser;
  }

  public final User getCurrentCustomer() {
    return currentUserProperty().get();
  }

  public final void setCurrentUser(User user) {
    currentUserProperty().set(user);
  }

  public String getUrlServer() {
    return urlServer;
  }

  public void setUrlServer(String urlServer) {
    this.urlServer = urlServer;
  }
}
