package ui.main;

import classification.Classificator;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import wekatextclassification.Main;
import utils.WebScraping;

public class MainController extends SplitPane {

    //region Variables
    
    private Classificator naiveBayesClassificator;
    
    // Reference to the main application.
    private Main mainApp;

    //endregion
    
    
    //region JavaFX controls
    
    @FXML
    private TextArea textTextArea;
    @FXML
    private TextField searchTextBox;
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblResult;

    //endregion
    
    
    //region Constructors
    
    public MainController() {
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
    public void setModels(Classificator naiveBayesClassificator1) {
        naiveBayesClassificator = naiveBayesClassificator1;
    }

    /**
     * Used to remove result of classification.
     */
    private void resetResult() {
        lblResult.setText("");
    }
    
    
    private void classify(String text) {
        try {
            String result;

            long startTime = System.currentTimeMillis();

            System.out.println("Bayes");
            result = naiveBayesClassificator.classify(text);
    
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            
            System.out.println(totalTime);
            
            long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
            long mseconds = totalTime - TimeUnit.SECONDS.toMillis(seconds);
            String time = "Time: " + String.format("%02d.%03d s", seconds, mseconds);
            System.out.println(time);

            System.out.println(result);
            
            lblResult.setText(result);
            
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
    
    //region UI handler methods
    
    @FXML
    protected void search() {
        String searchText = searchTextBox.getText();
        classify(searchText);
        System.out.println(searchText);
    }

    //endregion
}
