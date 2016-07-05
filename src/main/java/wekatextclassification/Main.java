package wekatextclassification;

import classification.Classificator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.layout.RootLayoutController;
import ui.main.MainController;
import ui.settings.WekaAppSettings;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private Classificator naiveBayesClassificator;
    private Classificator svmClassificator;
    private final String NAIVE_BAYES_MODEL_PATH = "resources/weka-models/filtered-bayes-25-5.model";
    private final String SVM_MODEL_PATH = "resources/weka-models/filtered-smo-25-5.model";
    private final String ICON_IMAGE_PATH = "file:resources/images/icon.png";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Set the application title.
        this.primaryStage.setTitle("Text classification - Weka");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image(ICON_IMAGE_PATH));

        loadModels();

        initRootLayout();

        showMainOverview();
    }

    /**
     * Loads classification models.
     */
    private void loadModels() {
        //Initialize new instance
        naiveBayesClassificator = new Classificator();
        //Check if there is prefered file to be loaded
        String naiveBayesPath = WekaAppSettings.getNaiveBayesModelPath();
        if(naiveBayesPath != null && !naiveBayesPath.equals("")){
            naiveBayesClassificator.loadModel(naiveBayesPath);
        }
        else {
            naiveBayesClassificator.loadModel(NAIVE_BAYES_MODEL_PATH);
        }

        //Initialize new instance
        svmClassificator = new Classificator();
        //Check if there is prefered file to be loaded
        String svmPath = WekaAppSettings.getSvmModelPath();
        if(svmPath != null && !svmPath.equals("")){
            svmClassificator.loadModel(svmPath);
        }
        else {
            svmClassificator.loadModel(SVM_MODEL_PATH);
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            controller.setModels(naiveBayesClassificator, svmClassificator);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showMainOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/main.fxml"));
            AnchorPane mainPage = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            MainController controller = loader.getController();
            controller.setMainApp(this);
            controller.setModels(naiveBayesClassificator, svmClassificator);

            // Set main page into the center of root layout.
            rootLayout.setCenter(mainPage);
        } catch (IOException e) {
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
