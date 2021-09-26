/**
 * Class DictionaryCommandLine.
 */
public class DictionaryCommandLine {
  public void dictionaryBasic() {
    DictionaryManagement.insertFromCommandline();
    showAllWords();
  }

  /**
   * showAllWords() function, hien thi tat ca tu hien co trong tu dien.
   */
  public void showAllWords() {
    System.out.println("No    |   English   |   Vietnamese    ");
    int dictionarySize = Dictionary.getSize();
    for (int i = 0; i < dictionarySize; i++) {
      Word temp = Dictionary.getWord(i);
      System.out.println(Integer.toString(i) + "   |   "
          + temp.getEngWord() + "   |   "
          + temp.getVietWord());
    }
    System.out.println("---------------------");
  }
}
