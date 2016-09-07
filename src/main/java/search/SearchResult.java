/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

/**
 *
 * @author Nemanja
 */
public class SearchResult {
    public String Title;
    public String Content;
    public float Score;
    
    public SearchResult(String title, String content, float score){
        Title = title;
        Content = content;
        Score = score;
    }
}
