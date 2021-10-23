import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.*;

/**
 * Dictionary class for dictionary management.
 */
public class Dictionary {
  // Since java handles BOM like other chars and db encoding is UTF-8
  public static final String UTF8_BOM = "\uFEFF";
  //Pattern to filer input data.
  //Match space(s) at the beginning + ending, characters except a-z, \', <space>, -, and .
  static Pattern pattern = Pattern.compile("^\s+|[^a-z'. -]|\s+$");
  private static Database database;
  private static final WordTrie trie = new WordTrie();

  static {
    try {
      Class.forName("org.sqlite.JDBC");
      database = new Database("jdbc:sqlite:D:\\dict.db");
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
    eng = eng.toLowerCase();
    if (filterInput(eng)) {
      System.err.println("Invalid word!");
      return;
    }
    if (eng.isEmpty()) {
      return;
    }
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
    eng = eng.toLowerCase();
    if (filterInput(eng)) {
      return "";
    }
    if (eng.isEmpty()) {
      return "";
    }
    if (trie.search(eng)) {
      return database.getMeaning(eng);
    }
    return "";
  }

  /**
   * Deleting word from trie and database.
   *
   * @param eng eng
   */
  public static void deleteWord(String eng) throws SQLException {
    eng = eng.toLowerCase();
    if (filterInput(eng)) {
      return;
    }
    if (eng.isEmpty()) {
      return;
    }
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
    eng = eng.toLowerCase();
    if (filterInput(eng)) {
      return new ArrayList<String>();
    }
    if (eng != null && eng.length() > 0) {
      return trie.getRecommendation(eng);
    }
    return new ArrayList<String>();
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
  public static void changeWord(String oldEng, String oldVie, String newEng, String newVie, String pron)
      throws SQLException {
    newEng = newEng.toLowerCase();
    if (filterInput(newEng)) {
      System.err.println("Invalid word!");
      return;
    }
    trie.delete(trie.root, oldEng, 0);
    trie.insert(newEng);
    database.delete(oldEng);
    database.add(newEng, newVie, "", pron);
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
      if (filterInput(word)) {
        //Deny all words violate the rules.
        continue;
      }
      trie.insert(word);
    }
  }

  public static boolean filterInput(String word) {
    return pattern.matcher(word).find();
  }

  public static String searchPronouce(String eng) throws SQLException {
    return database.getPronounce(eng);
  }
}
