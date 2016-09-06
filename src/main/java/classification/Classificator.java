package classification;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classificator class used for classifying text documents using Weka API.
 */
public class Classificator {

    /**
     * Object that stores the instance.
     */
    Instances instances;
    /**
     * Object that stores the classifier.
     */
    FilteredClassifier classifier;

    private Boolean initialized = false;

    private List<String> classes;

    public Classificator() {
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

    public List<String> getClasses() {
        return classes;
    }

    public Boolean isInitialized() {
        return initialized;
    }

    /**
     * This method creates the instance to be classified, from the text that has
     * been read.
     */
    private void makeInstance(String text) {
        // Create the attributes, class and text

        FastVector fvNominalVal = new FastVector(classes.size());
        for (int i = 0; i < classes.size(); i++) {
            fvNominalVal.addElement(classes.get(i));
        }

        Attribute attribute1 = new Attribute("text", (FastVector) null);
        Attribute attribute2 = new Attribute("class", fvNominalVal);
        // Create list of instances with one element
        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute1);
        fvWekaAttributes.addElement(attribute2);
        instances = new Instances("Test relation", fvWekaAttributes, 1);
        // Set class index
        instances.setClassIndex(1);
        // Create and add the instance
        Instance instance = new Instance(2);
        instance.setValue(attribute1, text);

        instances.add(instance);
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

            initialized = true;
        } catch (IOException | ClassNotFoundException e) {
            // Given the cast, a ClassNotFoundException must be caught along with the IOException
            System.out.println("Problem found when reading: " + fileName);

            initialized = false;
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
            makeInstance(text);
            
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
