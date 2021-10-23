import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.xml.crypto.Data;


public class Dictionary {
    public static final String UTF8_BOM = "\uFEFF"; //Since java handles BOM like other chars
    static private Database database;
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
    static private WordTrie trie = new WordTrie();
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
     * @param eng eng
     * @param vie vie
     */
    public static void addWord(String eng, String vie) throws SQLException {
        trie.insert(eng);
        database.add(eng, vie, " ", " ");
    }

    /**
     * Get a word's meaning.
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
     * @param eng eng
     */
    public static void deleteWord (String eng) throws SQLException {
        if (trie.search(eng)) {
            trie.delete(trie.root, eng, 0);
            database.delete(eng);
        }
    }

    /**
     * Look up word.
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
     * Change word.
     * @param eng eng
     * @param vie vie
     */
    public static void changeMeaning(String eng, String vie) throws SQLException {
        database.delete(eng);
        database.add(eng, vie, "a", "a");
    }

    public static void importData() throws SQLException {
        int num = 0;
        String word;
        ResultSet r = database.getAllWords();
        while (r.next() && num < 100) {
            word = r.getString("word");
            trie.insert(word);
            //num++;
        }
    }
}
