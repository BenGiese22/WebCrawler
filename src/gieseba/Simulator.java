/*
 * CS2852
 * Spring 2017
 * Lab 9 - DNS Server
 * Ben Giese
 * 5/11/2017
*/

package gieseba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Simulator extends Application {
    private static final String SCENE_TITLE = "Domain Name System";
    private static final double SCENE_WIDTH = 700;
    private static final double SCENE_HEIGHT = 300;
    private static final String SCENE_FXML = "simulator.fxml";


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the Application
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(SCENE_FXML));
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setTitle(SCENE_TITLE);
        primaryStage.show();

    }
}
