import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class test implements Initializable{
    @FXML
    private WebView  browser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Một đoạn text html:

        String html = "<html><h1>Hello</h1><h2>Hello</h2></html>";
        WebEngine webEngine = browser.getEngine();

        // Tải nội dung html tĩnh.
        webEngine.loadContent(html);
    }
}