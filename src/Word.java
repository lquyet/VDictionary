/**
 * Class Word luu tru cac tu trong tu dien.
 */
public class Word {
  private final String word_target;
  private final String word_explain;

  /**
   * Constructor cho Word voi param la tu tieng Anh va tieg Viet.
   *
   * @param eng tu tieng Anh
   * @param viet tu tieng Viet
   */
  Word(String eng, String viet) {
    word_target = eng;
    word_explain = viet;
  }

  public String getEngWord() {
    return word_target;
  }

  public String getVietWord() {
    return word_explain;
  }
}
