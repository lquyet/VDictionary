import java.util.Scanner;

/** Class DictionaryManagement chua cac thao tac voi tu dien. */
public class DictionaryManagement {

  /** Function insertFromCommandLine() them 1 tu vao tu dien. */
  public static void insertFromCommandline() {
    Scanner input = new Scanner(System.in);
    // Them tinh nang nhap nhieu tu cung luc theo requirements.
    System.out.println("Nhập số lượng từ vựng: ");
    int numOfWords = input.nextInt();
    input.nextLine();
    for (int i = 0; i < numOfWords; i++) {
      String eng = input.nextLine();
      String viet = input.nextLine();
      Dictionary.addWord(eng, viet);
    }
  }

  public static void insertFromFile() {

  }
  public static void dictionaryLookup(){

  }
  public static void dictionaryAdvanced(){

  }
  public static void dictionarySearcher() {

  }
  public static void dictionaryExportToFile(){

  }
  public static void dictionaryAdd(){

  }
  public static void dictionaryChange(){

  }
  public static void dictionaryDelete(){

  }
}
