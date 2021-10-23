import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * ChangeScene class for changing word event.
 */
public class ChangeScene implements Initializable {
  private static String eng;
  private static String vie;

  @FXML private TextArea engTextFrom;
  @FXML private TextArea engTextTo;
  @FXML private TextArea vieTextFrom;
  @FXML private TextArea vieTextTo;
  @FXML private AnchorPane ap;

  public static void changeText(String eng1, String vie1) {
    eng = eng1;
    vie = vie1;
  }

  /** Initialize when show Change Scene. */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    engTextFrom.setText(eng);
    engTextTo.setText(eng);
    vieTextFrom.setText(vie);

    engTextFrom.setEditable(false);
    engTextFrom.setMouseTransparent(true);
    engTextFrom.setFocusTraversable(false);

    vieTextFrom.setEditable(false);
    vieTextFrom.setMouseTransparent(true);
    vieTextFrom.setFocusTraversable(false);
  }

  /** Handling event when click CHANGE Button. */
  public void changeControl() throws SQLException {
    if (engTextTo.getText() != null
        && engTextTo.getText().length() > 0
        && vieTextTo.getText() != null
        && vieTextTo.getText().length() > 0) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Change Word");
      alert.setHeaderText("Do you want to change this word?");
      if (alert.showAndWait().get() == ButtonType.OK) {
        Dictionary.changeWord(
            engTextFrom.getText(), vieTextFrom.getText(), engTextTo.getText(), vieTextTo.getText());
        System.out.println("Changing complete!");
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
      }

    } else {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Change Word");
      alert.setHeaderText("Please fill in new Vietnamese meaning box ");
      if (alert.showAndWait().get() == ButtonType.OK) {
        System.out.println("Continue Changing...");
      }
    }
  }

  /** Handling event when click CANCEL Button. */
  public void cancelControl() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Cancel changing word");
    alert.setHeaderText("Do you want to cancel?");

    if (alert.showAndWait().get() == ButtonType.OK) {
      System.out.println("Cancel changing complete!");
      Stage stage = (Stage) ap.getScene().getWindow();
      stage.close();
    }
  }
}
