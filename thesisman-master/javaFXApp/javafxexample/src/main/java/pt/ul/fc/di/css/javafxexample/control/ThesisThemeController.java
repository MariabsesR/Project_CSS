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

public class ThesisThemeController {

  @FXML private ListView<ThesisTheme> themeListView;
  @FXML private Label errorLabel;

  private ThesisThemesService thesisThemeService = new ThesisThemesService();
  private DataModel dataModel;
  private ScreenController screenController;

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
    thesisThemeService.setDataModel(dataModel);
  }

  public void fetchThesisThemes() {
    Long userId = dataModel.getCurrentCustomer().getId();
    CompletableFuture.runAsync(
        () -> {
          try {
            List<ThesisTheme> thesisThemes = thesisThemeService.fetchThesisThemes(userId);
            Platform.runLater(
                () -> {
                  updateThesisThemesList(thesisThemes);
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

  private void updateThesisThemesList(List<ThesisTheme> thesisThemes) {
    themeListView.getItems().clear();
    themeListView.getItems().addAll(thesisThemes);
  }

  public void submitThesisTheme() {
    ThesisTheme selectedTheme = themeListView.getSelectionModel().getSelectedItem();
    if (selectedTheme != null) {
      User currentUser = dataModel.getCurrentCustomer();
      if (currentUser != null) {
        Long userId = currentUser.getId();
        CompletableFuture.runAsync(
            () -> {
              try {
                thesisThemeService.submitThesisTheme(selectedTheme, userId);
                Platform.runLater(
                    () -> {
                      fetchThesisThemes();
                      errorLabel.setText("Thesis theme submitted successfully!");
                    });
              } catch (IOException e) {
                Platform.runLater(
                    () -> {
                      errorLabel.setText("Failed to submit thesis theme.");
                      e.printStackTrace();
                    });
              }
            });
      } else {
        errorLabel.setText("User is not logged in.");
      }
    } else {
      errorLabel.setText("Please select a thesis theme before submitting.");
    }
  }

  public void setScreenController(ScreenController screenController) {
    this.screenController = screenController;
  }

  public void returnToMenu() {
    errorLabel.setText("");
    screenController.activate("Menu");
  }
}
