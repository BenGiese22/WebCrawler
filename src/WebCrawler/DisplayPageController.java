/*
 * Summer 2017
 * Ben Giese
 * 6/5/2017
*/
package WebCrawler;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

/**
 * Displays all the text from the accessed website.
 *
 * @author gieseba
 */
public class DisplayPageController implements Initializable {

    public static TextArea textArea;
    public static Button exitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // TODO
    }

    public void exitButton(ActionEvent event) {
        Platform.exit();
    }
}
