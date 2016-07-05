package ui.main;

import classification.Classificator;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import wekatextclassification.Main;
import utils.WebScraping;

public class MainController extends SplitPane {

    //region Variables
    
    private WebScraping webScraping;
    private Classificator naiveBayesClassificator;
    private Classificator svmClassificator;

    // Reference to the main application.
    private Main mainApp;

    //endregion
    
    
    //region JavaFX controls
    
    @FXML
    private Button btnClassify;
    @FXML
    private RadioButton naiveBayesBtn;
    @FXML
    private RadioButton svmBtn;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblResultTime;
    @FXML
    private TextArea textTextArea;
    @FXML
    private TextField urlTextBox;
    @FXML
    private Button btnDownloadText;

    //endregion
    
    
    //region Constructors
    
    public MainController() {
        webScraping = new WebScraping();
    }

    //endregion
    
    
    //region Methods
    
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called by the main application to give a reference for Classificators.
     *
     * @param naiveBayesClassificator1
     * @param svmClassificator1
     */
    public void setModels(Classificator naiveBayesClassificator1, Classificator svmClassificator1) {
        naiveBayesClassificator = naiveBayesClassificator1;
        svmClassificator = svmClassificator1;

        checkIfClassifyIsEnabled();
    }

    /**
     * Used for checking if classificators are initialized.
     */
    private void checkIfClassifyIsEnabled() {
        if (naiveBayesBtn.isSelected()) {
            if (naiveBayesClassificator != null && naiveBayesClassificator.isInitialized()) {
                btnClassify.setDisable(false);
            } else {
                btnClassify.setDisable(true);
            }
        } else if (svmClassificator != null && svmClassificator.isInitialized()) {
            btnClassify.setDisable(false);
        } else {
            btnClassify.setDisable(true);
        }
    }

    /**
     * Used to remove result of classification.
     */
    private void resetResult() {
        //Restart result value when different algoritam selected
        lblResult.setText("");
        lblResultTime.setText("");
    }

    //endregion
    
    //region UI handler methods
    @FXML
    protected void naiveBayesRadioButtonSelected() {
        checkIfClassifyIsEnabled();
        resetResult();
    }

    @FXML
    protected void svmRadioButtonSelected() {
        checkIfClassifyIsEnabled();
        resetResult();
    }

    @FXML
    protected void getTextFromUrl() {
        String text = webScraping.getContent(urlTextBox.getText());
        textTextArea.setText(text);
        resetResult();
    }

    @FXML
    protected void classify() {
        try {
            String result;

            long startTime = System.currentTimeMillis();

            if (naiveBayesBtn.isSelected()) {
                System.out.println("Bayes");
                result = naiveBayesClassificator.classify(textTextArea.getText());
            } else {
                System.out.println("SVM");
                result = svmClassificator.classify(textTextArea.getText());
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            
            System.out.println(totalTime);
            
            long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
            long mseconds = totalTime - TimeUnit.SECONDS.toMillis(seconds);
            String time = "Time: " + String.format("%02d:%03d ms", seconds, mseconds);
            System.out.println(time);

            lblResult.setText(result);
            lblResultTime.setText(time);
        } catch (Exception ex) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Classification failed");
            alert.setHeaderText("Please make sure that models are properly loaded.");
            alert.setContentText("You can load weka models using File -> Load Naive Bayes model or File -> Load SVM model\n"
                    + "Then make sure that correct algorithm is used for classification.");

            alert.showAndWait();
        }
    }

    //endregion
}
