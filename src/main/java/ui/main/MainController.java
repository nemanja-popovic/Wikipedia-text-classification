package ui.main;

import classification.Classificator;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import search.SearchResult;
import search.SearchResults;
import search.TextSearch;
import wekatextclassification.Main;
import utils.WebScraping;

public class MainController extends SplitPane {

    //region Variables
    private Classificator naiveBayesClassificator;

    // Reference to the main application.
    private Main mainApp;

    private TextSearch textSearch;

    SearchResults result;
    ObservableList<String> resultsListData;

    //endregion
    //region JavaFX controls
    @FXML
    private TextArea textTextArea;
    @FXML
    private TextField searchTextBox;
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblClass;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblTime;
    @FXML
    private ListView lstResults;
    @FXML
    private TextArea txtDocumentDetails;

    //endregion
    //region Constructors
    public MainController() {
        resultsListData = FXCollections.observableArrayList();
        textSearch = new TextSearch();
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
        lblClass.setText("Class: ");
        lblResult.setText("Results: ");
        lblTime.setText("Time: ");
        resultsListData.clear();
        txtDocumentDetails.clear();
    }

    private String classify(String text) {
        String result = "";
        try {

            System.out.println("Bayes");
            result = naiveBayesClassificator.classify(text);

            System.out.println(result);

            lblClass.setText("Class: " + result);

            //Now do search with Lucene and show results
        } catch (Exception ex) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Classification failed");
            alert.setHeaderText("Please make sure that models are properly loaded.");
            alert.setContentText("You can load weka models using File -> Load Naive Bayes model or File -> Load SVM model\n"
                    + "Then make sure that correct algorithm is used for classification.");

            alert.showAndWait();
        } finally {
            return result;
        }
    }

    //endregion
    //region UI handler methods
    @FXML
    protected void search() {
        //Reset previous results
        resetResult();

        //Get text
        String searchText = searchTextBox.getText();

        long startTime = System.currentTimeMillis();

        //Classify with naive bayes
        String classified = classify(searchText);
        //Search with lucene
        result = textSearch.Search(classified, searchText);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
        long mseconds = totalTime - TimeUnit.SECONDS.toMillis(seconds);
        String time = "Time: " + String.format("%02d.%03d s", seconds, mseconds);
        //Set time needed for classification and search
        lblTime.setText(time);

        //Show in list results
        lblResult.setText("Results: " + result.TotalHits);

        for (SearchResult searchResult : result.SearchResults) {
            System.out.println("Title: " + searchResult.Title);
            resultsListData.add(searchResult.Title);
        }

        //Set list data
        lstResults.setItems(resultsListData);
    }

    @FXML
    public void listMouseClick(MouseEvent arg0) {
        int index = lstResults.getSelectionModel().getSelectedIndex();
        if (result != null && result.SearchResults != null && !result.SearchResults.isEmpty()) {
            SearchResult selectedDocument = result.SearchResults.get(index);
            txtDocumentDetails.setText(selectedDocument.Content);
            System.out.println(index);
            System.out.println("clicked on " + lstResults.getSelectionModel().getSelectedItem());
        }
    }

    //endregion
}
