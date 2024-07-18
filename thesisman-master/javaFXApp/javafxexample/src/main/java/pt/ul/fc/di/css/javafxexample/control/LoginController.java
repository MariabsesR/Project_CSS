package pt.ul.fc.di.css.javafxexample.control;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.ul.fc.di.css.javafxexample.AuthService;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.User;

public class LoginController {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Label errorLabel;

  private ScreenController screenController;
  private AuthService authService;
  private DataModel dataModel;

  public LoginController() {
    authService = new AuthService();
  }

  @FXML
  private void handleLoginButtonAction(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    String credentialsJson =
        "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

    CompletableFuture.supplyAsync(
            () -> {
              try {
                return authService.authenticate(credentialsJson);
              } catch (IOException e) {
                throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
              }
            })
        .thenAcceptAsync(
            response -> {
              if (response != null && !response.startsWith("Error")) {
                try {
                  Long userId = Long.parseLong(response);
                  User currentUser = new User(username, password, userId);
                  dataModel.setCurrentUser(currentUser);
                  Platform.runLater(
                      () -> {
                        System.out.println("Authentication successful going to Menu");
                        screenController.activate("Menu");
                      });
                } catch (NumberFormatException e) {
                  Platform.runLater(() -> errorLabel.setText("Invalid response from server"));
                }
              } else {
                Platform.runLater(() -> errorLabel.setText("Invalid username or password"));
              }
            })
        .exceptionally(
            ex -> {
              Platform.runLater(
                  () -> errorLabel.setText("Authentication failed: " + ex.getMessage()));
              return null;
            });
  }

  public void setScreenController(ScreenController screenController) {
    this.screenController = screenController;
  }

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
    authService.setDataModel(dataModel);
  }
}
