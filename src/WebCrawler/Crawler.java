package WebCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

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

        System.out.println(parseDoc(nfdlWikiSite));

        try {
            System.out.println(onlyParagraphs(getURLSource(nfdlWikiSite)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(doc.select("<p>"));
        //System.out.println(doc.title() + "\n");
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
        for (Element link : links) {
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
        for (Element image : images) {
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

    private static String getWebsiteURL() {
        return doc.location();
    }

    private static String getURLSource(String url) throws IOException {
        URL tempURL = new URL(nfdlWikiSite);
        URLConnection tempC = tempURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(tempC.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while((inputLine = in.readLine()) !=null) {
            a.append(inputLine + "\n");

        }
        in.close();
        return a.toString();
    }

    private static String onlyParagraphs(String sourceCode) {
        Document tempDoc = Jsoup.parse(sourceCode);
        StringBuilder a = new StringBuilder();
        for( Element element : tempDoc.select("p")) {
            a.append(element.text() + "\n");
        }
        return a.toString();
    }

    private static String parseDoc(String sourceCode) {
        return Jsoup.parse(sourceCode).text();
    }
}