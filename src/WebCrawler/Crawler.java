package WebCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by bengi on 5/29/2017.
 */
public class Crawler {

    private static Document doc = null;
    private static String nfdlWikiSite = "https://en.m.wikipedia.org/wiki/North_Fond_du_Lac,_Wisconsin";
    private static String temp = "http://msoe.us/taylor/cs2852/";
    private static String websiteURL = "";

    public Crawler(String url) {
        websiteURL = url;
        loadWebpage(websiteURL);
    }

    public static void main(String[] args) {

        try {
            doc = Jsoup.connect(nfdlWikiSite).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(getTitle());
        printTotalLinks();
        printTotalImages();
        //System.out.println(title + "\n");
        //System.out.println(doc.toString());
    }

    private static void loadWebpage(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Not a valid Website");
        }
    }

    private static Elements totalLinks() {
        return doc.select("a[href]");
    }

    private static void printTotalLinks() {
        Elements links = totalLinks();
        for(Element link : links) {
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text());
        }
    }

    //Throws null pointer
    private static void printMetaInfo() {
        String keywords = doc.select("meta[name=keywords]").first().attr("content");
        System.out.println("Meta keyword : " + keywords);
        String description = doc.select("meta[name=description]").get(0).attr("content");
        System.out.println("Meta description : " + description);
    }

    private static Elements totalImages() {
        return doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
    }

    private static void printTotalImages() {
        Elements images = totalImages();
        for(Element image : images) {
            System.out.println("\nsrc : " + image.attr("src"));
            System.out.println("height : " + image.attr("height"));
            System.out.println("width : " + image.attr("width"));
            System.out.println("alt : " + image.attr("alt"));
        }
    }

    private static String getTitle() {
        return doc.title();
    }

    private static String getBodyText() {
        return doc.body().text();
    }

}
