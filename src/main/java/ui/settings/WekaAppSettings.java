package ui.settings;

import wekatextclassification.Main;

import java.util.prefs.Preferences;

public class WekaAppSettings {

    private static final String NAIVE_BAYES_MODEL_PATH_KEY = "naiveBayesModel";
    private static final String SVM_MODEL_PATH_KEY = "svmModel";
    private static final String LAST_OPENED_FOLDER = "lastOpenedFolder";

    public WekaAppSettings() { }

    public static String getNaiveBayesModelPath(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String naiveBayesModelPath1 = prefs.get(NAIVE_BAYES_MODEL_PATH_KEY, null);
        if (naiveBayesModelPath1 != null) {
            return naiveBayesModelPath1;
        } else {
            return null;
        }
    }

    public static void setNaiveBayesModelPath(String naiveBayesModelPath){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (naiveBayesModelPath != null) {
            prefs.put(NAIVE_BAYES_MODEL_PATH_KEY, naiveBayesModelPath);
        } else {
            prefs.remove(NAIVE_BAYES_MODEL_PATH_KEY);
        }
    }

    public static String getSvmModelPath(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String svmModelPath = prefs.get(SVM_MODEL_PATH_KEY, null);
        if (svmModelPath != null) {
            return svmModelPath;
        } else {
            return null;
        }
    }

    public static void setSvmModelPath(String svmModelPath1){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (svmModelPath1 != null) {
            prefs.put(SVM_MODEL_PATH_KEY, svmModelPath1);
        } else {
            prefs.remove(SVM_MODEL_PATH_KEY);
        }
    }

    public static String getLastOpenedFolder(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String lastFolder = prefs.get(LAST_OPENED_FOLDER, null);
        if (lastFolder != null) {
            return lastFolder;
        } else {
            return "";
        }
    }

    public static void setLastOpenedFolder(String lastFolder){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (lastFolder != null) {
            prefs.put(LAST_OPENED_FOLDER, lastFolder);
        } else {
            prefs.remove(LAST_OPENED_FOLDER);
        }
    }

}
