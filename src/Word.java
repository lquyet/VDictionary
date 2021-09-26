/**
 * Class Word luu tru cac tu trong tu dien.
 */
public class Word {
  // Bo "final" de co the sua tu va nghia.
  private String word_target;
  private String word_explain;

  Word(String eng, String viet) {
    word_target = eng;
    word_explain = viet;
  }
}
