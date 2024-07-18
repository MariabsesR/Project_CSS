package pt.ul.fc.di.css.javafxexample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.control.DocumentController;
import pt.ul.fc.di.css.javafxexample.control.LoginController;
import pt.ul.fc.di.css.javafxexample.control.MenuController;
import pt.ul.fc.di.css.javafxexample.control.ScreenController;
import pt.ul.fc.di.css.javafxexample.control.ThesisThemeController;
import pt.ul.fc.di.css.javafxexample.control.UserThesisThemeController;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class Hello extends Application {

  private DataModel dataModel;
  private String url;

  @Override
  public void init() throws Exception {
    Parameters params = getParameters();
    if (!params.getRaw().isEmpty()) {
      url = params.getRaw().get(0);

    } else {
      throw new IllegalArgumentException("URL argument is missing!");
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    dataModel = new DataModel();
    dataModel.setUrlServer(url);

    ScreenController screenController = new ScreenController(primaryStage);

    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
    BorderPane loginRoot = loginLoader.load();
    LoginController loginController = loginLoader.getController();
    loginController.setDataModel(dataModel);
    loginController.setScreenController(screenController);
    Scene loginScene = new Scene(loginRoot, 400, 300);
    screenController.addScene("Login", loginScene);

    FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("view/menu.fxml"));
    BorderPane menuRoot = menuLoader.load();
    MenuController menuController = menuLoader.getController();
    menuController.setScreenController(screenController);
    Scene menuScene = new Scene(menuRoot, 800, 600);
    screenController.addScene("Menu", menuScene);

    FXMLLoader thesisThemesLoader =
        new FXMLLoader(getClass().getResource("view/ThesisThemesList.fxml"));
    BorderPane thesisThemesRoot = thesisThemesLoader.load();
    ThesisThemeController thesisThemeController = thesisThemesLoader.getController();
    thesisThemeController.setDataModel(dataModel);
    thesisThemeController.setScreenController(screenController);
    Scene thesisThemeScene = new Scene(thesisThemesRoot, 800, 600);
    screenController.addScene("ThesisThemesList", thesisThemeScene);

    FXMLLoader userThesisThemesLoader =
        new FXMLLoader(getClass().getResource("view/UserThesisThemes.fxml"));
    BorderPane userThesisThemesRoot = userThesisThemesLoader.load();
    UserThesisThemeController userThesisThemeController = userThesisThemesLoader.getController();
    userThesisThemeController.setDataModel(dataModel);
    userThesisThemeController.setScreenController(screenController);
    Scene userThesisThemeScene = new Scene(userThesisThemesRoot, 800, 600);
    screenController.addScene("UserThesisThemesList", userThesisThemeScene);

    menuController.setThesisThemeController(thesisThemeController);
    menuController.setUserThesisThemeController(userThesisThemeController);

    // add submit documents
    FXMLLoader documentLoader =
        new FXMLLoader(getClass().getResource("view/DocumentSubmission.fxml"));
    BorderPane documentRoot = documentLoader.load();
    DocumentController documentController = documentLoader.getController();
    documentController.setDataModel(dataModel);

    documentController.setScreenController(screenController);
    Scene documentScene = new Scene(documentRoot, 400, 300);
    screenController.addScene("Document", documentScene);

    screenController.activate("Login");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
