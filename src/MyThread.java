import java.sql.SQLException;

/**
 * Multi-threading to load data while playing intro video.
 */
public class MyThread extends Thread {
  @Override
  public void run() {
    try {
      Dictionary.importData();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
