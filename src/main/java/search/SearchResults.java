/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;

/**
 *
 * @author Nemanja
 */
public class SearchResults {

    public int TotalHits;
    public ArrayList<SearchResult> SearchResults;

    public SearchResults(int totalHits, ArrayList<SearchResult> searchResults) {
        TotalHits = totalHits;
        SearchResults = searchResults;
    }
}
