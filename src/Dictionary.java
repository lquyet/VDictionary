/**
 * Class Dictionary luu tru mang Word.
 */
public class Dictionary {
  private static Word[] words;
  private static int size;

  /**
   * addWord() function, them 1 tu vao tu dien.
   *
   * @param eng  tu tieng Anh
   * @param viet  nghia tieng Viet
   */
  public static void addWord(String eng, String viet) {
    words = new Word[size + 1];
    words[size] = new Word(eng, viet);
    size++;
  }

  public static int getSize() {
    return size;
  }

  /**
   * getWord() function tra ve obj Word tai vi tri index.
   *
   * @param index  vi tri cua Word obj
   * @return Word obj tai index
   */
  public static Word getWord(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    return words[index];
  }

}
