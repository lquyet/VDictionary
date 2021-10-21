import java.sql.*;

public class Database {
  private Connection conn;
  private Statement statement;

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

}
