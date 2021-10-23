import java.util.ArrayList;

public class Word {
    private String vie;
    private String eng;
    private Word[] childs = new Word[28];

    public Word() {
        for (int i = 0; i < 28; ++i) {
            childs[i] = null;
            vie = null;
        }
    }

    public void add(String eng, String vie, int pos) {
        if (pos == eng.length()) {
            this.vie = vie;
            this.eng = eng;
        }
        else {
            int x = eng.charAt(pos) - 'a';
            if (eng.charAt(pos) == ' ') {
                x = 26;
            }
            else if (eng.charAt(pos) == '-') {
                x = 27;
            }
            else if (x < 0 || x > 27) {
                return;
            }
            if (childs[x] == null) {
                childs[x] = new Word();
            }
            childs[x].add(eng, vie, pos + 1);
        }
    }

    public void change(String eng, String vie, int pos) {
        if (pos == eng.length()) {
            this.vie = vie;
            this.eng = eng;
        }
        else {
            int x = eng.charAt(pos) - 'a';
            if (eng.charAt(pos) == ' ') {
                x = 26;
            }
            else if (eng.charAt(pos) == '-') {
                x = 27;
            }
            else if (x < 0 || x > 27) {
                return;
            }
            childs[x].change(eng, vie, pos + 1);
        }
    }

    public String search(String eng, int pos) {
        if (pos == eng.length()) {
            return vie;
        }
        else {
            int x = eng.charAt(pos) - 'a';
            if (eng.charAt(pos) == ' ') {
                x = 26;
            }
            else if (eng.charAt(pos) == '-') {
                x = 27;
            }
            else if (x < 0 || x > 27) {
                return null;
            }
            if (childs[x] == null) {
                return null;
            }
            return childs[x].search(eng, pos + 1);
        }
    }

    public void delete (String eng, int pos) {
        if (pos == eng.length()) {
            this.vie = null;
            this.eng = null;
        }
        else {
            int x = eng.charAt(pos) - 'a';
            if (eng.charAt(pos) == ' ') {
                x = 26;
            }
            else if (eng.charAt(pos) == '-') {
                x = 27;
            }
            else if (x < 0 || x > 27) {
                return;
            }
            childs[x].delete(eng, pos + 1);
            int dem = 0;
            for (int i = 0; i < 28; ++i) {
                if (childs[i] != null) {
                    dem++;
                }
            }
            if (dem == 1) {
                childs[x] = null;
            }
        }
    }

    public ArrayList<String> lookUp(String eng, int pos, int sz) {
        if (sz >= 12) return new ArrayList<String>();
        if (pos >= eng.length()) {
            int szz = sz;
            ArrayList<String> ans = new ArrayList<>();
            if (this.eng != null) {
                szz++;
                ans.add(this.eng);
            }
            for (int i = 0; i < 28; ++i) {
                if (childs[i] != null && szz < 12) {
                    ArrayList<String> x = childs[i].lookUp(eng, pos + 1, szz);
                    szz += x.size();
                    ans.addAll(x);
                }
            }
            return ans;
        }
        else {
            int x = eng.charAt(pos) - 'a';
            if (eng.charAt(pos) == ' ') {
                x = 26;
            }
            else if (eng.charAt(pos) == '-') {
                x = 27;
            }
            else if (x < 0 || x > 27) {
                return new ArrayList<String>();
            }
            if (childs[x] == null) return new ArrayList<String>();
            return childs[x].lookUp(eng, pos + 1, 0);
        }
    }

}
