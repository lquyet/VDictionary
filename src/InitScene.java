import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class InitScene implements Initializable {

	@FXML
	private MediaView mediaView;
	
	@FXML private Button playButton;

	@FXML private ImageView gg;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
            Dictionary.addWordFromFile("D:\\data.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
		file = new File("src/video.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(() -> {
			mediaView.setVisible(false);
			switchToSearchScene();
		});
		mediaView.setMediaPlayer(mediaPlayer);
	}
	
	public void playMedia() {
		mediaPlayer.play();
        playButton.setVisible(false);
		gg.setVisible(false);
	}

	public void switchToSearchScene() {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
			Stage stage = (Stage) mediaView.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
