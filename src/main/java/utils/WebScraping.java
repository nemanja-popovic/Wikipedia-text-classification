package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Nemanja Popovic on 5/26/2016.
 */
public class WebScraping {
    private String selector = "div[id=content]";

    /**
     * This method gets selector that is used to parse web page.
     * @return Selector that is used for filtering content.
     */
    public String getSelector(){
        return selector;
    }

    /**
     * This method sets selector that is used to parse web page.
     * @param newSelector String that will be used as selector. Eg. ("div[id=content]").
     */
    public void setSelector(String newSelector){
        selector = newSelector;
    }

    /**
     * This method gets content from given web page.
     * @param url Url of the web site that should be returned.
     * @return Content from given web site.
     */
    public String getContent(String url){
        String content = "";
        
        System.out.println("Get url: " + url);

        try {
            Document doc = Jsoup.connect(url).get();
            Element contentDiv = doc.select(selector).first();
            content = contentDiv.text(); // The result
            
            System.out.println(content);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return content;
    }
}
