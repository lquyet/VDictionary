package GUI;

import java.sql.SQLException;

import Client.Core.Dictionary;

/**
 * Multi-threading to load data while playing intro video.
 */
public class LoadDbThread extends Thread {
  @Override
  public void run() {
    try {
      Dictionary.importData();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
