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
 * Created by Nemanja Popovic on 5/26/2016.
 */
public class Classificator {

    /**
     * String that stores the text to classify
     */
    String text;
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
    
    public List<String> GetClasses(){
        return classes;
    }

    public Boolean isInitialized(){
        return initialized;
    }

    /**
     * This method sets text that should be classified.
     * @param newText The text that should be classified.
     */
    private void setText(String newText){
        text = newText;
    }

    /**
     * This method creates the instance to be classified, from the text that has been read.
     */
    private void makeInstance() {
        // Create the attributes, class and text
        
        FastVector fvNominalVal = new FastVector(classes.size());
        for (int i = 0; i < classes.size(); i++) {
            fvNominalVal.addElement(classes.get(i));
        }
        
//        FastVector fvNominalVal = new FastVector(10);
//        fvNominalVal.addElement("architecture");
//        fvNominalVal.addElement("art");
//        fvNominalVal.addElement("biology");
//        fvNominalVal.addElement("chemistry");
//        fvNominalVal.addElement("computer science");
//        fvNominalVal.addElement("literature");
//        fvNominalVal.addElement("mathematics");
//        fvNominalVal.addElement("music");
//        fvNominalVal.addElement("philosophy");
//        fvNominalVal.addElement("physics");

        Attribute attribute1 = new Attribute("text",(FastVector) null);
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
        // Another way to do it:
        // instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
        instances.add(instance);
        System.out.println("===== Instance created with reference dataset =====");
        System.out.println(instances);
    }

    /**
     * This method loads the model to be used as classifier.
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
        }
        catch (IOException | ClassNotFoundException e) {
            // Given the cast, a ClassNotFoundException must be caught along with the IOException
            System.out.println("Problem found when reading: " + fileName);

            initialized = false;
        }
    }

    /**
     * This method performs the classification of the instance.
     * Output is done at the command-line.
     * @param text Text that should be classified.
     * @return Classification output.
     */
    public String classify(String text) {
        try {
            setText(text);
            makeInstance();

            double pred = classifier.classifyInstance(instances.instance(0));
            System.out.println("===== Classified instance =====");
            System.out.println("Class predicted: " + instances.classAttribute().value((int) pred));
            return instances.classAttribute().value((int) pred);
        }
        catch (Exception e) {
            System.out.println("Problem found when classifying the text");
            return "";
        }
    }
}
