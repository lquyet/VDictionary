/**
 * Class Dictionary luu tru mang Word.
 */
public class Dictionary {
  private Word[] words;
  private int size;

  public void addWord(String anh, String viet) {
    words = new Word[size + 1];
    words[size] = new Word(anh, viet);
  }
}
