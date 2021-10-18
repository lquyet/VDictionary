import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SearchScene implements Initializable{
    @FXML
    private TextField mySearchWord;
    @FXML
    private TextArea myTranslateWord;
    @FXML
    private ListView myListView;
    @FXML
    private Label myLabel;
    @FXML
    private AnchorPane ap;

    /**
     * Initialize when go to Search Scene.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Dictionary.addWordFromFile("D:\\data.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        myTranslateWord.setEditable(false);
        myTranslateWord.setMouseTransparent(true);
        myTranslateWord.setFocusTraversable(false);
        myListView.setVisible(false);
    }

    /**
     * Searching word when click Enter or click on ListView
     */
    public void searchWord() {
        String eng = mySearchWord.getText();
        String vie = Dictionary.searchWord(eng);
        myTranslateWord.setText(vie);
        if (vie != null) {
            myLabel.setText(eng);
        }
        else {
            myLabel.setText("Can't not find this word!");
        }
        mySearchWord.setText("");
    }

    /**
     * look up HINT word when typing on searching bar.
     */
    public void lookUpWord() {        
        mySearchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.length() == 0) {
                myListView.setVisible(false);
            }
            else {
                myListView.setVisible(true);
            }
            myListView.getItems().clear();
            ArrayList<String>ans = Dictionary.lookUpWord(newValue);
            if (ans != null) {
                myListView.getItems().addAll(ans);
            }
        });
    }
    
    /**
     * Handle when click on the ListView.
     * @param arg0 mouse event
     */
    public void handleMouseClick(MouseEvent arg0) {
        String x = (String) myListView.getSelectionModel().getSelectedItem();
        myLabel.setText(x);
        mySearchWord.setText(x);
        searchWord();
    }

    /**
     * Switch to AddScene.
     */
    public void addScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddScene.fxml"));
            
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());
            
            Stage stage = new Stage() ;
            stage.getIcons().add(new Image("icon.png"));
            stage.setTitle("Adding Word");
            stage.setScene(scene);

            myLabel.setText("");
            myTranslateWord.setText("");

            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to Change Scene.
     */
    public void changeScene() {
        if (myLabel == null || myLabel.getText().length() == 0
            || myLabel.getText() == "Can't not find this word!") {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Changing word");
            alert.setHeaderText("There is no word to change!");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("Continue Searching...");
            }

            return;
        }
        try {

            ChangeScene.changeText(myLabel.getText(), myTranslateWord.getText());
            myLabel.setText("");
            myTranslateWord.setText("");
            myLabel.setText("");
            myTranslateWord.setText("");

            Parent root = FXMLLoader.load(getClass().getResource("ChangeScene.fxml"));
            
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());
            
            Stage stage = new Stage() ;
            stage.getIcons().add(new Image("icon.png"));
            stage.setTitle("Change Word");
            stage.setScene(scene);

            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deleting the word show in label
     */
    public void deleteWord() {
        if (myLabel == null || myLabel.getText().length() == 0
            || myLabel.getText() == "Can't not find this word!") {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Deleting word");
            alert.setHeaderText("There is no word to delete!");

            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("Continue Searching...");
            }

            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deleting word");
        alert.setHeaderText("Do you want to DELETE" + '\"' + myLabel.getText() + "\"?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Dictionary.deleteWord(myLabel.getText());
            myLabel.setText("");
            myTranslateWord.setText("");
            System.out.println("Deleting complete!");
        }
    }
}