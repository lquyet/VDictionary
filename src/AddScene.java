import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Add Scene class for adding word event.
 */
public class AddScene {
  @FXML TextArea engText;
  @FXML TextArea proText;
  @FXML TextArea vieText;
  @FXML Button addButton;
  @FXML Button cancelButton;
  @FXML AnchorPane ap;

  /** Handling event when click ADD Button. */
  public void addControl() throws SQLException {
    if (engText != null && engText.getText().length() > 0 && !Dictionary.filterInput(engText.getText())) {
      String vie = Dictionary.searchWord(engText.getText());
      if (!vie.isEmpty()) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Same Word");
        alert.setHeaderText(
            "This word is already in the dictionary!\nDo you want to change this word's meaning?");

        if (alert.showAndWait().get() == ButtonType.OK) {
          System.out.println("Change Scene");
          ChangeScene.changeText(engText.getText(), Dictionary.searchWord(engText.getText()), 
          Dictionary.searchPronouce(engText.getText()));
          
          changeScene();
          Stage stage = (Stage) ap.getScene().getWindow();
          stage.close();
        }
      } else {
        if (vieText.getText() != null && vieText.getText().length() > 0) {
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Add Word");
          alert.setHeaderText("Do you want to add this word to the dictionary?");
          if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Adding complete!");
            Dictionary.addWord(engText.getText(), vieText.getText(), proText.getText());
            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
          }
        } else {
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Add Word");
          alert.setHeaderText("Please fill in English and Vietnamese");
          if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Continue Adding...");
          }
        }
      }
    } else {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Invalid Word!");
      alert.setHeaderText("Please try again!");
      if (alert.showAndWait().get() == ButtonType.OK) {
        System.out.println("Continue Adding...");
      }
    }
  }

  /** Handling event when clock CANCEL Button. */
  public void cancelControl() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Cancel adding word");
    alert.setHeaderText("Do you want to cancel");

    if (alert.showAndWait().get() == ButtonType.OK) {
      System.out.println("Cancel adding complete!");
      Stage stage = (Stage) ap.getScene().getWindow();
      stage.close();
    }
  }

  /** Switch to Change Scene. */
  public void changeScene() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("ChangeScene.fxml"));

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
}
