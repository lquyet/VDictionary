import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());
            
            primaryStage.getIcons().add(new Image("icon.png"));
            primaryStage.setTitle("VDictionary");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                logOut(primaryStage);
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(Stage stage ) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout!");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.print("You successfully logged out!");
            stage.close();
        }
    }
}
