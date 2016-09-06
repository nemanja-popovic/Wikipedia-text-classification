/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Nemanja
 */
public class TextSearch {

    //Create all indexes and open them as soon as the class is initialized?
    private IndexReader indexReaderArchitecture;
    private IndexReader indexReaderArt;
    private IndexReader indexReaderBiology;
    private IndexReader indexReaderChemistry;
    private IndexReader indexReaderComputerScience;
    private IndexReader indexReaderLiterature;
    private IndexReader indexReaderMathematics;
    private IndexReader indexReaderMusic;
    private IndexReader indexReaderPhilosophy;
    private IndexReader indexReaderPhysics;
    private String BASE_INDEX_PATH = "resources/indexes";

    public TextSearch() {
        try {
            Directory directoryArchitecture = FSDirectory.open(new File(BASE_INDEX_PATH + "/architecture"));
            Directory directoryArt = FSDirectory.open(new File(BASE_INDEX_PATH + "/art"));
            Directory directoryBiology = FSDirectory.open(new File(BASE_INDEX_PATH + "/biology"));
            Directory directoryChemistry = FSDirectory.open(new File(BASE_INDEX_PATH + "/chemistry"));
            Directory directoryComputerScience = FSDirectory.open(new File(BASE_INDEX_PATH + "/computer science"));
            Directory directoryLiterature = FSDirectory.open(new File(BASE_INDEX_PATH + "/literature"));
            Directory directoryMathematics = FSDirectory.open(new File(BASE_INDEX_PATH + "/mathematics"));
            Directory directoryMusic = FSDirectory.open(new File(BASE_INDEX_PATH + "/music"));
            Directory directoryPhilosophy = FSDirectory.open(new File(BASE_INDEX_PATH + "/philosophy"));
            Directory directoryPhysics = FSDirectory.open(new File(BASE_INDEX_PATH + "/physics"));

            indexReaderArchitecture = DirectoryReader.open(directoryArchitecture);
            indexReaderArt = DirectoryReader.open(directoryArt);
            indexReaderBiology = DirectoryReader.open(directoryBiology);
            indexReaderChemistry = DirectoryReader.open(directoryChemistry);
            indexReaderComputerScience = DirectoryReader.open(directoryComputerScience);
            indexReaderLiterature = DirectoryReader.open(directoryLiterature);
            indexReaderMathematics = DirectoryReader.open(directoryMathematics);
            indexReaderMusic = DirectoryReader.open(directoryMusic);
            indexReaderPhilosophy = DirectoryReader.open(directoryPhilosophy);
            indexReaderPhysics = DirectoryReader.open(directoryPhysics);
        } catch (IOException ex) {
            Logger.getLogger(TextSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Search(String index,String text){
        if(index == ""){
            //Over all indexes
        }
        else{
            //Search only in this index
        }
    }
    
}
