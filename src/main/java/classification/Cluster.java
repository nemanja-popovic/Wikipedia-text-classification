/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

/**
 *
 * @author Nemanja
 */
public class Cluster {

    private final String MODEL_PATH = "D:\\Faks\\Suzana\\clusterWekaKMeans.model";

    /**
     * Object that stores the instance.
     */
    Instances instances;
    /**
     * Object that stores the classifier.
     */
    FilteredClassifier classifier;
    
    private List<String> classes;
    
    public Cluster() {
        classes = new ArrayList<String>();

        classes.add("architecture");
        classes.add("art");
        classes.add("biology");
        classes.add("chemistry");
        classes.add("computer science");
        classes.add("literature");
        classes.add("mathematics");
        classes.add("music");
        classes.add("philosophy");
        classes.add("physics");
    }

    /**
     * This method loads the model to be used as classifier.
     *
     * @param fileName The name of the file that stores the text.
     */
    public void loadModel(String fileName) {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                Object tmp = in.readObject();
                classifier = (FilteredClassifier) tmp;
            }
            System.out.println("===== Loaded model: " + fileName + " =====");
        } catch (IOException | ClassNotFoundException e) {
            // Given the cast, a ClassNotFoundException must be caught along with the IOException
            System.out.println("Problem found when reading: " + fileName);
        }
    }

    /**
     * This method performs the classification of the instance. Output is done
     * at the command-line.
     *
     * @param text Text that should be classified.
     * @return Classification output.
     */
    public String classify(String text) {
        try {
            //makeInstance(text);

            double pred = classifier.classifyInstance(instances.instance(0));
            //get the prediction percentage or distribution
            double[] percentage = classifier.distributionForInstance(instances.instance(0));

            //If prediction is > 0.2 then return predicted class
            if (percentage[(int) pred] > 0.2) {
                return instances.classAttribute().value((int) pred);
            } else {
                //We are not sure which class this query should be classified to
                //Because that search will be executed over all documents
                return "";
            }
        } catch (Exception e) {
            System.out.println("Problem found when classifying the text");
            return "";
        }
    }
}
