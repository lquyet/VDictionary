import java.sql.*;

public class Database {
  private Connection conn;
  private Statement statement;
  private int numOfRecords;

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

    //Get number of records
    ResultSet r = statement.executeQuery("SELECT COUNT(*) AS word FROM 'av';");
    numOfRecords = r.getInt("word");
  }

  /**
   * Run Select command.
   * @param command
   * @return A ResultSet object containing query results
   * @throws SQLException if failed
   */
  public ResultSet runQuery(String command) throws SQLException {
    ResultSet r = statement.executeQuery(command);
    return r;
  }

  /**
   * Run Update, Insert, and Delete commands.
   * @param command
   * @throws SQLException if failed
   */
  public void runUpdate(String command) throws SQLException {
    statement.executeUpdate(command);
    conn.commit(); //if not auto-commit mode
  }

  public void add(String eng, String vie, String html, String pronounce) throws SQLException {
    String cmd = "INSERT INTO 'av' (word, html, description, pronounce)"
        + "VALUES ('"
        + eng
        + "','"
        + html
        + "','"
        + vie
        + "','"
        + pronounce
        +"');";
    runUpdate(cmd);
  }

  public void delete(String eng) throws SQLException {
    String cmd = "DELETE FROM 'av' where word = '" + eng + "';";
    runUpdate(cmd);
  }

  public String getMeaning(String eng) throws SQLException {
    String tmp = "";
    String cmd = "SELECT * FROM 'av' where word = '" + eng + "';";
    ResultSet r = runQuery(cmd);
    while (r.next()) {
      tmp = r.getString("description");
    }
    return tmp;
  }

  public ResultSet getAllWords() throws SQLException {
    String cmd = "SELECT * FROM 'av';";
    ResultSet r = runQuery(cmd);
    return r;
  }
}
