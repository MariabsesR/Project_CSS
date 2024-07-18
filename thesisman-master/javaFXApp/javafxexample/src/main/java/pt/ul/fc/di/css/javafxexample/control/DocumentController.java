package pt.ul.fc.di.css.javafxexample.control;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.FileSubmissionService;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class DocumentController {
  @FXML private Button submitProposalButton;

  @FXML private Button submitFinalButton;

  @FXML private Label errorLabel;

  @FXML private TextField filePathField;
  private DataModel dataModel;
  private File selectedFile;
  private Stage stage;
  private FileSubmissionService fileSubmission;
  private ScreenController screenController;

  public DocumentController() {
    this.fileSubmission = new FileSubmissionService();
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  @FXML
  private void handleSelectFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Document");
    fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
    selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
      filePathField.setText(selectedFile.getAbsolutePath());
      errorLabel.setText("");
    } else {
      filePathField.setText("");
      errorLabel.setText("File selection cancelled.");
    }
  }

  @FXML
  private void handleSubmitProposal() {
    submitFile("ThesisProposal");
  }

  @FXML
  private void handleSubmitFinal() {
    submitFile("ThesisFinal");
  }

  private void submitFile(String documentType) {
    if (selectedFile != null) {
      try {
        Long userId = dataModel.getCurrentCustomer().getId();
        String response = fileSubmission.uploadFile(selectedFile, documentType, userId);
        errorLabel.setText(selectedFile.getName() + ": " + response);
        selectedFile = null;
        filePathField.setText("");
      } catch (IOException e) {
        errorLabel.setText("File upload failed: " + e.getMessage());
      }
    } else {
      errorLabel.setText("No file selected. Plese select a file to submit.");
    }
  }

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
    fileSubmission.setDataModel(dataModel);
  }

  public void setScreenController(ScreenController screenController) {
    this.screenController = screenController;
  }

  public void returnToMenu() {
    errorLabel.setText("");
    screenController.activate("Menu");
  }
}
