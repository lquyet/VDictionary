import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dictionary class for dictionary management.
 */
public class Dictionary {
  // Since java handles BOM like other chars and db encoding is UTF-8
  public static final String UTF8_BOM = "\uFEFF";
  private static Database database;
  private static final WordTrie trie = new WordTrie();

  static {
    try {
      Class.forName("org.sqlite.JDBC");
      database = new Database("jdbc:sqlite:C:\\Users\\anhqu\\Desktop\\dict2.db");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /*
  Dictionary(String url) {
      try {
          database = new Database("jdbc:sqlite:C:\\Users\\anhqu\\Desktop\\dbTest\\src\\dict.db");
      }
      catch (SQLException throwables) {
          throwables.printStackTrace();
          System.err.println("Database init failed");
          System.exit(0);
      }
      trie = new WordTrie();
  }
   */

  /**
   * Adding word to trie and database.
   *
   * @param eng eng
   * @param vie vie
   */
  public static void addWord(String eng, String vie, String pronounce) throws SQLException {
    trie.insert(eng);
    database.add(eng, vie, " ", pronounce);
  }

  /**
   * Get a word's meaning.
   *
   * @param eng eng
   * @return vietnamese word
   */
  public static String searchWord(String eng) throws SQLException {
    if (trie.search(eng)) {
      return database.getMeaning(eng);
    }
    return null;
  }

  /**
   * Deleting word from trie and database.
   *
   * @param eng eng
   */
  public static void deleteWord(String eng) throws SQLException {
    if (trie.search(eng)) {
      trie.delete(trie.root, eng, 0);
      database.delete(eng);
    }
  }

  /**
   * Look up word.
   *
   * @param eng eng
   * @return array list of String
   */
  public static ArrayList<String> lookUpWord(String eng) {
    if (eng != null && eng.length() > 0) {
      return trie.getRecommendation(eng);
    }
    return null;
  }

  /**
   * Change an English word and its meaning in Vietnamese.
   *
   * @param oldEng old English word
   * @param oldVie old Vietnamese meaning
   * @param newEng new English word
   * @param newVie new Vietnamese meaning
   * @throws SQLException if fail
   */
  public static void changeWord(String oldEng, String oldVie, String newEng, String newVie)
      throws SQLException {
    trie.delete(trie.root, oldEng, 0);
    trie.insert(newEng);
    database.delete(oldEng);
    database.add(newEng, newVie, "", "");
  }

  /**
   * Insert all words from database to trie.
   *
   * @throws SQLException if fail
   */
  public static void importData() throws SQLException {
    String word;
    ResultSet r = database.getAllWords();
    while (r.next()) {
      word = r.getString("word");
      trie.insert(word);
    }
  }
}
