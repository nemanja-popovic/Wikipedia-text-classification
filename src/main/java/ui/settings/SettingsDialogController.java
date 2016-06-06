package ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class SettingsDialogController extends SplitPane {

    //region Variables

    private Stage dialogStage;
    private boolean okClicked = false;
    
    private final String ICON_IMAGE_PATH = "file:src/main/resources/images/icon.png";

    //endregion

    //region JavaFX controls

    @FXML
    private TextField naiveBayesModelField;
    @FXML
    private TextField svmModelField;

    //endregion

    //region Constructors

    public SettingsDialogController(){
    }

    //endregion

    //region Methods

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        // Set the application title.
        this.dialogStage.setTitle("Settings");

        // Set the application icon.
        this.dialogStage.getIcons().add(new Image(ICON_IMAGE_PATH));
    }

    /**
     * Sets the settings to be edited in the dialog.
     */
    public void loadSettings() {
        naiveBayesModelField.setText(WekaAppSettings.getNaiveBayesModelPath());
        svmModelField.setText(WekaAppSettings.getSvmModelPath());
    }


    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (naiveBayesModelField.getText() == null || naiveBayesModelField.getText().length() == 0) {
            errorMessage += "Default Naive Bayes model will be used!\n";
        }
        if (svmModelField.getText() == null || svmModelField.getText().length() == 0) {
            errorMessage += "Default SVM model will be used!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the confirmation box.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Are you sure you want to use defaults?");
            alert.setContentText(errorMessage);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                return true;
            }

            return false;
        }
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    //endregion

    //region UI handler methods

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        loadSettings();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            WekaAppSettings.setNaiveBayesModelPath(naiveBayesModelField.getText());
            WekaAppSettings.setSvmModelPath(svmModelField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    //endregion

}
