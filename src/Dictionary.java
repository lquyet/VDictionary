/**
 * Class Dictionary luu tru mang Word.
 */
public class Dictionary {
  private static Word[] words;
  private static int size;

  public static void addWord(String eng, String viet) {
    words = new Word[size + 1];
    words[size] = new Word(eng, viet);
    size++;
  }
}
