/**
 * Created by bengi on 5/29/2017.
 */
package WebCrawler;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * TODO Add Class Javadocs
 *
 * @author bengi
 */
public class SpiderController implements Initializable {

    Crawler crawler = new Crawler("https://en.m.wikipedia.org/wiki/North_Fond_du_Lac,_Wisconsin");

    private static final String ACCESSEDWEBSITESLIST_FXML = "AccessedWebsitesList.fxml";

    ObservableList<String> websitesEntered = FXCollections.observableArrayList();

    public TextField websiteTextField;
    public MenuBar menuBar;
    public Menu fileMenu;
    public MenuItem closeMenuItem;
    public ListView listView;
    public Menu helpMenu;
    public MenuItem aboutMenuItem;
    public Button accessedWebsites;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //ListView<String> list = new ListView<>();

    }

    public void websiteTextField(ActionEvent event) {
        //Adding to ListView for accessed websites
        String temp = websiteTextField.getText();
        websitesEntered.add(temp);
        listView.setItems(websitesEntered);
    }

    public void closeMenuItem(ActionEvent event) {
        Platform.exit();
    }

    public void accessedWebsites(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ACCESSEDWEBSITESLIST_FXML));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Accessed Websites");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aboutMenuItem(ActionEvent event) {
        //version control?
    }


}