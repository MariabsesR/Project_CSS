package pt.ul.fc.di.css.javafxexample.control;

public class MenuController {

  private ScreenController screenController;
  private ThesisThemeController thesisThemeController;
  private UserThesisThemeController userThesisThemeController;

  public void setScreenController(ScreenController screenController) {

    this.screenController = screenController;
  }

  public void setThesisThemeController(ThesisThemeController thesisThemeController) {
    this.thesisThemeController = thesisThemeController;
  }

  public void setUserThesisThemeController(UserThesisThemeController userThesisThemeController) {
    this.userThesisThemeController = userThesisThemeController;
  }

  public void viewThesisThemeList() {
    System.out.println("Switching to theiss themes");
    ThesisThemeController controller = thesisThemeController;
    controller.fetchThesisThemes();
    screenController.activate("ThesisThemesList");
    System.out.println("Switched to theiss themes");
  }

  public void viewCurrentCandidaturas() {
    UserThesisThemeController controller = userThesisThemeController;
    controller.fetchThesisThemesCurrentUser();
    screenController.activate("UserThesisThemesList");
    System.out.println("Switching to current candidaturas themes");
  }

  public void viewSubmitDocuments() {
    System.out.println("Switching to submiut documents");
    screenController.activate("Document");
  }
}
