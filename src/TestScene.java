import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TestScene implements Initializable{

    @FXML Label myTopLabel;
    @FXML Label myLeftLabel;
    @FXML Label myRightLabel;
    @FXML Label myBotLabel;
    @FXML Label myMidLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSize(myTopLabel);
        setSize(myBotLabel);
        setSize(myLeftLabel);
        setSize(myRightLabel);
        setSize(myMidLabel);
    }

    public void setSize(Label myLabel) {
        myLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(myLabel, 0.0);
        AnchorPane.setRightAnchor(myLabel, 0.0);
        myLabel.setAlignment(Pos.CENTER);
    }
    
}
