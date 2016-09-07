/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.surround.parser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

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

    Logger logger = Logger.getLogger("WekaTextClassifier");

    //D:\Faks\Suzana\WekaTextClassification\WekaTextClassification\resources\indexes
    private String BASE_INDEX_PATH = "resources/indexes/";
    //private String BASE_INDEX_PATH = "D:\\Faks\\Suzana\\WekaTextClassification\\WekaTextClassification\\resources\\indexes\\";

    private Analyzer analyzer;

    private HashMap<String, IndexSearcher> _indexSearchers;

    private final int hitsPerPage = 10000;

    public TextSearch() {
        try {
            logger.addHandler(new FileHandler("D:/WekaTextCLassification.log"));

            _indexSearchers = new HashMap<String, IndexSearcher>();
            analyzer = new WhitespaceAnalyzer();

            Directory directoryArchitecture = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "architecture"));
            Directory directoryArt = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "art"));
            Directory directoryBiology = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "biology"));
            Directory directoryChemistry = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "chemistry"));
            Directory directoryComputerScience = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "computer science"));
            Directory directoryLiterature = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "literature"));
            Directory directoryMathematics = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "mathematics"));
            Directory directoryMusic = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "music"));
            Directory directoryPhilosophy = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "philosophy"));
            Directory directoryPhysics = FSDirectory.open(Paths.get(BASE_INDEX_PATH + "physics"));

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

            _indexSearchers.put("architecture", new IndexSearcher(indexReaderArchitecture));
            _indexSearchers.put("art", new IndexSearcher(indexReaderArt));
            _indexSearchers.put("biology", new IndexSearcher(indexReaderBiology));
            _indexSearchers.put("chemistry", new IndexSearcher(indexReaderChemistry));
            _indexSearchers.put("computer science", new IndexSearcher(indexReaderComputerScience));
            _indexSearchers.put("literature", new IndexSearcher(indexReaderLiterature));
            _indexSearchers.put("mathematics", new IndexSearcher(indexReaderMathematics));
            _indexSearchers.put("music", new IndexSearcher(indexReaderMusic));
            _indexSearchers.put("philosophy", new IndexSearcher(indexReaderPhilosophy));
            _indexSearchers.put("physics", new IndexSearcher(indexReaderPhysics));
            _indexSearchers.put("", new IndexSearcher(new MultiReader(indexReaderArchitecture,
                    indexReaderArt, indexReaderBiology, indexReaderChemistry, indexReaderComputerScience,
                    indexReaderLiterature, indexReaderMathematics, indexReaderMusic,
                    indexReaderPhilosophy, indexReaderPhysics)));

            logger.log(Level.FINE, "Lucene Index loaded ok.");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public SearchResults Search(String index, String text) {

        ArrayList<SearchResult> searchItems = new ArrayList<SearchResult>();
        SearchResults result = new SearchResults(0, searchItems);

        if (!_indexSearchers.containsKey(index)) {
            System.out.println("No index found");
            System.out.println(index);
            index = "";
        }

        IndexSearcher searcher = _indexSearchers.get(index);
        org.apache.lucene.queryparser.classic.QueryParser parser = new org.apache.lucene.queryparser.classic.QueryParser("content", analyzer);
        Query query;
        try {
            query = parser.parse(text);

            //Search
            TopDocs docs = searcher.search(query, hitsPerPage, Sort.RELEVANCE);

            ScoreDoc[] hits = docs.scoreDocs;
            int end = Math.min(docs.totalHits, hitsPerPage);

            for (int i = 0; i < end; i++) {
                Document d = searcher.doc(hits[i].doc);
                searchItems.add(new SearchResult(d.get("title"), d.get("content"), hits[i].score));
            }

            result.TotalHits = docs.totalHits;
            result.SearchResults = searchItems;
            
            logger.log(Level.FINE, "Search done on text: " + text);
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

}
