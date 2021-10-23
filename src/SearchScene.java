import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * SearchScene class for searching event.
 */
public class SearchScene implements Initializable {
  @FXML private TextField mySearchWord;
  @FXML private TextArea myTranslateWord;
  @FXML private ListView myListView;
  @FXML private Label myLabel;
  @FXML private Label myPronounceLabel;
  @FXML private AnchorPane ap;
  @FXML private Button deleteButton;
  @FXML private Button changeButton;
  @FXML private Button buttonUS;
  private Media musicMedia;
	private MediaPlayer musicMediaPlayer;

  /** Initialize when go to Search Scene. */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    myTranslateWord.setEditable(false);
    myListView.setVisible(false);
    myTranslateWord.setVisible(false);
    deleteButton.setVisible(false);
    changeButton.setVisible(false);
    buttonUS.setVisible(false);
  }

  /** Searching word when click Enter or click on ListView. */
  public void searchWord() throws SQLException {
    String eng = mySearchWord.getText();
    String vie = Dictionary.searchWord(eng);
    String pro = Dictionary.searchPronouce(eng);
    myTranslateWord.setText(vie);
    if (!vie.isEmpty()) {
      myLabel.setText(eng);
      myPronounceLabel.setText(pro);
      myPronounceLabel.setVisible(true); 
      myTranslateWord.setVisible(true);
      deleteButton.setVisible(true);
      changeButton.setVisible(true);
      buttonUS.setVisible(true);
    } else {
      myLabel.setText("Can't not find this word!");
      myPronounceLabel.setVisible(false);
      myTranslateWord.setVisible(false);
      deleteButton.setVisible(false);
      changeButton.setVisible(false);
      buttonUS.setVisible(false);
    }
    mySearchWord.setText("");
  }

  /** look up HINT word when typing on searching bar. */
  public void lookUpWord() {
    mySearchWord
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
                myListView.setVisible(newValue != null && newValue.length() != 0);
              myListView.getItems().clear();
              ArrayList<String> ans = Dictionary.lookUpWord(newValue);
              if (ans != null) {
                myListView.getItems().addAll(ans);
                myListView.setMaxHeight(ans.size() * 36);
              }
            });
  }

  /**
   * Handle when click on the ListView.
   *
   * @param arg0 mouse event
   */
  public void handleMouseClick(MouseEvent arg0) throws SQLException {
    String x = (String) myListView.getSelectionModel().getSelectedItem();
    myLabel.setText(x);
    mySearchWord.setText(x);
    searchWord();
  }

  /** Switch to AddScene. */
  public void addScene() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("AddScene.fxml"));

      Scene scene = new Scene(root);
      // scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());

      Stage stage = new Stage();
      stage.getIcons().add(new Image("icon.png"));
      stage.setTitle("Adding Word");
      stage.setScene(scene);

      myLabel.setText("");
      myTranslateWord.setText("");
      myTranslateWord.setVisible(false);
      myPronounceLabel.setVisible(false);
      deleteButton.setVisible(false);
      changeButton.setVisible(false);
      buttonUS.setVisible(false);

      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Switch to Change Scene. */
  public void changeScene() {
    if (myLabel == null
        || myLabel.getText().length() == 0
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

      ChangeScene.changeText(myLabel.getText(), myTranslateWord.getText(), myPronounceLabel.getText());
      myLabel.setText("");
      myTranslateWord.setText("");
      myTranslateWord.setVisible(false);
      myPronounceLabel.setVisible(false);
      deleteButton.setVisible(false);
      changeButton.setVisible(false);
      buttonUS.setVisible(false);

      // since getClass() might be null, we should filter it like this
      Parent root = FXMLLoader.load(
          Objects.requireNonNull(getClass().getResource("ChangeScene.fxml")));

      Scene scene = new Scene(root);
      // scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());

      Stage stage = new Stage();
      stage.getIcons().add(new Image("icon.png"));
      stage.setTitle("Change Word");
      stage.setScene(scene);

      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Deleting the word show in label. */
  public void deleteWord() throws SQLException {
    if (myLabel == null
        || myLabel.getText().length() == 0
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
      myTranslateWord.setVisible(false);
      myPronounceLabel.setVisible(false);
      deleteButton.setVisible(false);
      changeButton.setVisible(false);
      buttonUS.setVisible(false);
      System.out.println("Deleting complete!");
    }
  }

  /**voice speak when press button */
  public void speak() {
    Translator.textToSpeech(myLabel.getText());
    File file = new File("sound.mp3");
    musicMedia = new Media(file.toURI().toString());
		musicMediaPlayer = new MediaPlayer(musicMedia);
    musicMediaPlayer.play();
  }

  /**switch to search paragraph scene */
  public void searchParagraph() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("SearchParagraph.fxml"));

      Scene scene = new Scene(root);
      // scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());

      Stage stage = new Stage();
      stage.getIcons().add(new Image("icon.png"));
      stage.setTitle("Search Paragraph");
      stage.setScene(scene);

      myLabel.setText("");
      myTranslateWord.setText("");
      myTranslateWord.setVisible(false);
      myPronounceLabel.setVisible(false);
      deleteButton.setVisible(false);
      changeButton.setVisible(false);
      buttonUS.setVisible(false);

      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
