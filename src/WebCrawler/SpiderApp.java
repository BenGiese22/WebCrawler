/**
 * Created by bengi on 5/29/2017.
 */
package WebCrawler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpiderApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(SpiderApp.class.getName());
    private static final String SCENE_TITLE = "Website Crawler";
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 400;
    private static final String SCENE_FXML = "Spider.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(SCENE_FXML));
            primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            primaryStage.setTitle(SCENE_TITLE);
            primaryStage.show();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}