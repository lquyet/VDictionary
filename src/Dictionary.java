import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class Dictionary luu tru cau truc/
 */
public class Dictionary {
    private static Word root = new Word();

    /**
     * Adding word.
     * @param eng eng
     * @param vie vie
     */
    public static void addWord(String eng, String vie) {
        root.add(eng, vie, 0);
    }

    /**
     * Searching word.
     * @param eng eng
     * @return eng
     */
    public static String searchWord(String eng) {
        if (eng != null) return root.search(eng, 0);
        return null;
    }

    /**
     * Deleting word.
     * @param eng eng
     */
    public static void deleteWord (String eng) {
        root.delete(eng, 0);
    }

    /**
     * Add word from file
     * @param file file direction
     * @throws IOException
     */
    public static void addWordFromFile(String file) throws IOException {
        ArrayList<Pair> arr = new ArrayList<Pair>();

        //read file txt
        File myObj = new File(file);
        Scanner myReader = new Scanner(myObj);

        String eng = "";
        String vie = "";
        int dem = 0;
        int d = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            d++;
            if (d == 1)data = data.substring(1, data.length());
            if (data.length() > 0) {
                if (data.charAt(0) == '@') {
                    
                    if (eng.length() > 0) {
                        dem++;
                        arr.add(new Pair(eng, vie, dem));
                    }
                    
                    vie = "";
                    eng = "";
                    boolean dk = true;
                    for (int i = 1; i < data.length(); ++i) {
                        if (data.charAt(i) == '/') dk = false;
                        if (dk) eng += data.charAt(i);
                        else vie += data.charAt(i);
                    }
                    
                    if (eng.length() > 0) {
                        int begin = 0;
                        while ((eng.charAt(begin) < 'a' || eng.charAt(begin) > 'z')
                                && (eng.charAt(begin) < 'A' || eng.charAt(begin) > 'Z')) {
                            begin++;
                        }
                        int last = eng.length() - 1;
                        while ((eng.charAt(last) < 'a' || eng.charAt(last) > 'z')
                                && (eng.charAt(last) < 'A' || eng.charAt(last) > 'Z')) {
                            last--;
                        }
                        eng = eng.substring(begin, last + 1);
                        eng = eng.toLowerCase();
                        boolean notRequired = false;
                        //System.out.println(eng);
                        for (int i = 0; i < eng.length(); ++i) {
                            if (eng.charAt(i) < 'a' || eng.charAt(i) > 'z') {
                                if (eng.charAt(i) != ' ' && eng.charAt(i) != '-')
                                {
                                    notRequired = true;
                                }
                            }
                        }
                        if (notRequired) {
                            eng = "";
                        }
                    }

                }
                else vie += "\n" + data;
            }
        }

        Collections.sort(arr, Pair.PairComparator);
        for (int i = 0; i < arr.size(); ++i) {
            int point = i;
            String e = arr.get(i).getEng();
            String v = "";
            while (point < arr.size() && arr.get(point).getEng().equals(arr.get(i).getEng())) {
                v += '\n' + arr.get(point).getVie();
                point++;
            }
            //System.out.println(v);
            addWord(e, v);
            i = point - 1;
        }
        System.out.println("Document inserted successfully");
    }

    /**
     * Look up word.
     * @param eng eng 
     * @return array list of String
     */
    public static ArrayList<String> lookUpWord(String eng) {
        if (eng != null && eng.length() > 0) {
            return root.lookUp(eng, 0, 0);
        }
        return null;
    }

    /**
     * Change word.
     * @param eng eng
     * @param vie vie
     */
    public static void changeWord(String eng, String vie) {
        root.change(eng, vie, 0);
    }
}
