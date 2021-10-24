package Client.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database class store database of the app.
 */
public class Database {
  private final Connection conn;
  private final Statement statement;
  private final int numOfRecords;

  /**
   * Constructor for an Database object.
   *
   * @param url path to .db file
   * @throws SQLException if connection failed
   */
  Database(String url) throws SQLException {
    conn = DriverManager.getConnection(url);
    statement = conn.createStatement();
    conn.setAutoCommit(false);

    // Get number of records
    ResultSet r = statement.executeQuery("SELECT COUNT(*) AS word FROM 'av';");
    numOfRecords = r.getInt("word");
  }

  /**
   * Run Select command.
   *
   * @param command sql command
   * @return A ResultSet object containing query results
   * @throws SQLException if failed
   */
  public ResultSet runQuery(String command) throws SQLException {
    ResultSet r = statement.executeQuery(command);
    return r;
  }

  /**
   * Run Update, Insert, and Delete commands.
   *
   * @param command sql command
   * @throws SQLException if failed
   */
  public void runUpdate(String command) throws SQLException {
    statement.executeUpdate(command);
    conn.commit(); // if not auto-commit mode
  }

  /**
   * Adding a word to sqlite database and to trie.
   *
   * @param eng English word
   * @param vie Vietnamese word
   * @param html html, not necessary
   * @param pronounce pronunciation
   * @throws SQLException exception if query fail
   */
  public void add(String eng, String vie, String html, String pronounce) throws SQLException {
    String cmd =
        "INSERT INTO 'av' (word, html, description, pronounce)"
            + "VALUES ('"
            + eng
            + "','"
            + html
            + "','"
            + vie
            + "','"
            + pronounce
            + "');";
    runUpdate(cmd);
  }

  public void delete(String eng) throws SQLException {
    String cmd = "DELETE FROM 'av' where word = '" + eng + "';";
    runUpdate(cmd);
  }

  /**
   * Query sqlite db for meaning of word.
   *
   * @param eng English word
   * @return String contains its meaning
   * @throws SQLException if query fail
   */
  public String getMeaning(String eng) throws SQLException {
    String tmp = "";
    String cmd = "SELECT * FROM 'av' where word = '" + eng + "';";
    ResultSet r = runQuery(cmd);
    while (r.next()) {
      tmp = r.getString("description");
    }
    return tmp;
  }

  /**
   * Read all records in sqlite database and insert to trie.
   *
   * @return a ResultSet object holding query results
   * @throws SQLException if query fail
   */
  public ResultSet getAllWords() throws SQLException {
    String cmd = "SELECT * FROM 'av';";
    ResultSet r = runQuery(cmd);
    return r;
  }

  public String getPronounce(String eng) throws SQLException {
    String t = "";
    String cmd = "SELECT pronounce FROM av where word = '" + eng +"';";
    ResultSet r = runQuery(cmd);
    while (r.next()) {
      t = r.getString("pronounce");
    }
    return t;
  }
}
