package Frontend;

import java.io.IOException;

import Backend.API.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class SearchParagraph {
    @FXML TextArea eng;
    @FXML TextArea vie;
    @FXML Button myButton;

    public void translate() throws IOException {
        if (eng != null && eng.getText().length() > 0) {
            vie.setText(Translator.translate("en", "vi", eng.getText()));
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Invalid Word!");
            alert.setHeaderText("Please try again!");

            if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Continue Translating");
            }
        }
    }
}
