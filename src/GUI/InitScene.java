package GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class InitScene implements Initializable {

  @FXML private MediaView mediaView;

  @FXML private Button playButton;

  @FXML private ImageView gg;

  @FXML private ImageView disc;

  @FXML private Label discText;

  private File file;
  private Media videoMedia;
  private MediaPlayer videoMediaPlayer;
  private Media musicMedia;
	private MediaPlayer musicMediaPlayer;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    file = new File("src/GUI/asserts/video2.mp4");
    videoMedia = new Media(file.toURI().toString());
    videoMediaPlayer = new MediaPlayer(videoMedia);
    videoMediaPlayer.setOnEndOfMedia(
        () -> {
          mediaView.setVisible(false);
          switchToSearchScene();
          musicMediaPlayer.setVolume(0.2);
        });
    mediaView.setMediaPlayer(videoMediaPlayer);

    file = new File("src/GUI/asserts/morningRoutine.mp3");
    musicMedia = new Media(file.toURI().toString());
		musicMediaPlayer = new MediaPlayer(musicMedia);
    musicMediaPlayer.play();
  }

  public void playMedia() {
    videoMediaPlayer.play();
    playButton.setVisible(false);
    gg.setVisible(false);
    disc.setVisible(false);
    discText.setVisible(false);
    LoadDbThread inputData = new LoadDbThread();
    inputData.start();
  }

  public void switchToSearchScene() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
      Stage stage = (Stage) mediaView.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
