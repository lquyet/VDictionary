package Frontend;

import java.sql.SQLException;

import Backend.Core.Dictionary;

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
