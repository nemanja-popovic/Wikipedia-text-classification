package ui.layout;

import classification.Classificator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import wekatextclassification.Main;
import ui.settings.SettingsDialogController;
import ui.settings.WekaAppSettings;

import java.io.File;
import java.io.IOException;

public class RootLayoutController extends SplitPane {

    //region Variables

    private Main mainApp;

    private Classificator naiveBayesClassificator;
    private Classificator svmClassificator;
    
    private final String ICON_IMAGE_PATH = "file:src/main/resources/images/icon.png";

    //endregion

    //region JavaFX controls

    @FXML
    private MenuItem menuCloseButton;
    @FXML
    private MenuItem menuSettingsButton;
    @FXML
    private MenuItem menuLoadNaiveBayesModel;
    @FXML
    private MenuItem menuLoadSVMModel;

    //endregion

    //region Constructors

    public RootLayoutController(){

    }

    //endregion

    //region Methods

    /**
     * Is called by the main application to give a reference for Classificators.
     *
     * @param naiveBayesClassificator1
     * @param svmClassificator1
     */
    public void setModels(Classificator naiveBayesClassificator1, Classificator svmClassificator1) {
        naiveBayesClassificator = naiveBayesClassificator1;
        svmClassificator = svmClassificator1;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showSettingsDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/SettingsDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.getIcons().add(new Image(ICON_IMAGE_PATH));

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SettingsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            return false;
        }
    }

    //endregion

    //region UI handler methods

    /**
     * Loads Naive Bayes model from file.
     */
    @FXML
    protected void handleLoadNaiveBayesModel(){
        try {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "WEKA Model (*.model)", "*.model");
            fileChooser.getExtensionFilters().add(extFilter);
            String folder = WekaAppSettings.getLastOpenedFolder();
            if (folder != null && !folder.equals("")) {
                fileChooser.setInitialDirectory(new File(folder));
            }

            // Show save file dialog
            File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

            if (file != null) {
                WekaAppSettings.setLastOpenedFolder(file.getParent());
//                WekaAppSettings.setNaiveBayesModelPath(file.getPath());
                naiveBayesClassificator.loadModel(file.getPath());
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Loads SVM model from file.
     */
    @FXML
    protected void handleLoadSVMModel() {
        try {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "WEKA Model (*.model)", "*.model");
            fileChooser.getExtensionFilters().add(extFilter);

            String folder = WekaAppSettings.getLastOpenedFolder();
            if (folder != null && !folder.equals("")) {
                fileChooser.setInitialDirectory(new File(folder));
            }

            // Show save file dialog
            File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

            if (file != null) {
                WekaAppSettings.setLastOpenedFolder(file.getParent());
//                WekaAppSettings.setSvmModelPath(file.getPath());
                svmClassificator.loadModel(file.getPath());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Opens settings dialog.
     */
    @FXML
    protected void handleSettings(){
        showSettingsDialog();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Weka Classifier");
        alert.setHeaderText("About");
        alert.setContentText("Author: Nemanja Popovic\n" +
                "Faculty of Electronic Engineering Nis Serbia");
        
        Stage aboutAppStage = (Stage) alert.getDialogPane().getScene().getWindow();
        aboutAppStage.getIcons().add(new Image(ICON_IMAGE_PATH));
       
        alert.showAndWait();
    }

    //endregion
}
