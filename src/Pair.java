import java.util.Comparator;

public class Pair {
    private String vie;
    private String eng;
    private int index;
    Pair (String eng, String vie , int index) {
        this.vie = vie;
        this.eng = eng;
        this.index = index;
    }

    public String getEng() {
        return eng;
    }

    public String getVie() {
        return vie;
    }

    public int getIndex() {
        return index;
    }

    public static Comparator<Pair> PairComparator = new Comparator<Pair>() {

        public int compare(Pair s1, Pair s2){
            String e1 = s1.getEng();
            String e2 = s2.getEng();
            for (int i = 0; i < Math.min(e1.length(), e2.length()); ++i) {
                if (e1.charAt(i) > e2.charAt(i))return 1;
                else if (e1.charAt(i) < e2.charAt(i)) return -1;
            }
            if (e1.length() > e2.length()) return 1;
            if (e2.length() > e1.length()) return -1;
            return s1.getIndex() - s2.getIndex();
        }
    };
}