package pt.ul.fc.di.css.javafxexample.control;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.ul.fc.di.css.javafxexample.ThesisThemesService;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.ThesisTheme;
import pt.ul.fc.di.css.javafxexample.model.User;

public class UserThesisThemeController {

  @FXML private ListView<ThesisTheme> themeListViewCurrentUser;
  @FXML private Label errorLabel;

  private ThesisThemesService thesisThemeService = new ThesisThemesService();
  private DataModel dataModel;
  private ScreenController screenController;

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
    thesisThemeService.setDataModel(dataModel);
  }

  public void fetchThesisThemesCurrentUser() {
    Long userId = dataModel.getCurrentCustomer().getId();
    CompletableFuture.runAsync(
        () -> {
          try {
            List<ThesisTheme> thesisThemes =
                thesisThemeService.fetchThesisThemesCurrentUser(userId);
            Platform.runLater(
                () -> {
                  updateUserThesisThemesList(thesisThemes);
                });
          } catch (IOException e) {
            Platform.runLater(
                () -> {
                  errorLabel.setText("Failed to fetch thesis themes.");
                  e.printStackTrace();
                });
          }
        });
  }

  private void updateUserThesisThemesList(List<ThesisTheme> thesisThemes) {
    themeListViewCurrentUser.getItems().clear();
    themeListViewCurrentUser.getItems().addAll(thesisThemes);
  }

  public void setScreenController(ScreenController screenController) {
    this.screenController = screenController;
  }

  public void returnToMenu() {
    errorLabel.setText("");
    screenController.activate("Menu");
  }

  public void cancelThesisApplication() {
    ThesisTheme selectedTheme = themeListViewCurrentUser.getSelectionModel().getSelectedItem();
    if (selectedTheme != null) {
      User currentUser = dataModel.getCurrentCustomer();
      if (currentUser != null) {
        Long userId = currentUser.getId();
        CompletableFuture.runAsync(
            () -> {
              try {
                thesisThemeService.deleteThesisTheme(userId, selectedTheme);
                Platform.runLater(
                    () -> {
                      fetchThesisThemesCurrentUser();
                      errorLabel.setText("Thesis theme deleted successfully!");
                    });
              } catch (IOException e) {
                Platform.runLater(
                    () -> {
                      errorLabel.setText("Failed to delete thesis theme.");
                      e.printStackTrace();
                    });
              }
            });
      } else {
        errorLabel.setText("User is not logged in.");
      }
    } else {
      errorLabel.setText("Please select a thesis theme before deleting.");
    }
  }
}
