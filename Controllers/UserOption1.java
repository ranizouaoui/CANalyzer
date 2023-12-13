package Controllers;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class UserOption1 implements Initializable {

    @FXML
    private WebView webView, webView1;
    @FXML
    private Accordion accordion; // Reference to the Accordion
    @FXML
    private TabPane tabPane; // Reference to the TabPane
    @FXML
    private Button switchTabButton; // Reference to the button
    @FXML
    private TitledPane firstTitledPane;
    
    public void initialize(URL arg0, ResourceBundle arg1) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.youtube.com/embed/ijsYg4yEWWc");
        WebEngine webEngine1 = webView1.getEngine();
        webEngine1.load("https://www.youtube.com/embed/1veVFMcxe3M");
        // Ensure the Accordion and first TitledPane are not null
        if (accordion != null && firstTitledPane != null) {
            // Open the first TitledPane programmatically
            accordion.setExpandedPane(firstTitledPane);
        }
    }
    
    @FXML
    private void switchToTab2() {
        // Switch to the second tab (index 1)
        tabPane.getSelectionModel().select(1);
    }
    @FXML
    private void switchToTab1() {
        // Switch to the second tab (index 1)
        tabPane.getSelectionModel().select(0);
    }
}
