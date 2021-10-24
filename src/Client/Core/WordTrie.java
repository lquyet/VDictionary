package Client.Core;

import java.util.ArrayList;

/**
 * Trie data structure to store all words from db.
 */
public class WordTrie {

  static final int NUM_OF_CHAR = 30; // 26 English letters + " " + "." + "-" + " ' ", respectively.
  static final int NUM_OF_RECOMMEND = 20; // recommend maximum 20 words
  // root of the trie
  public Node root;

  WordTrie() {
    root = new Node();
  }

  /**
   * Convert char to index.
   *
   * @param c char to convert
   * @return appropriate index
   */
  private static int formatIndex(char c) {
    if (c == ' ') {
      return 26;
    } else if (c == '-') {
      return 27;
    } else if (c == '.') {
      // c == '.'
      return 28;
    } else if (c == '\'') {
      return 29;
    }
    return (int) (c) - 'a';
  }

  /**
   * Convert index back to char.
   *
   * @param n index to convert
   * @return char
   */
  private static char intToChar(int n) {
    if (n == 26) {
      return ' ';
    } else if (n == 27) {
      return '-';
    } else if (n == 28) {
      return '.';
    } else if (n == 29) {
      return '\'';
    }
    return (char) (n + 'a');
  }

  /**
   * Insert a word to trie.
   *
   * @param word English word needed to insert
   */
  public void insert(String word) {
    // word.replace(UTF8_BOM, "");
    int level = 0;
    int depth = word.length();
    int index;

    Node travel = root;

    for (; level < depth; level++) {
      if (word.charAt(level) == '\uFEFF') {
        continue;
      }
      index = formatIndex(word.charAt(level));
      if (index < 0 || index > 29) {
        continue;
      }
      if (travel.children[index] == null) {
        travel.children[index] = new Node();
      }
      travel = travel.children[index]; // running throw the tree
    }

    // Confirm this is a word
    travel.isWord = true;
  }

  /**
   * Check if a word exists in trie.
   *
   * @param word English word to check
   * @return true if exists
   */
  public boolean search(String word) {
    int level = 0;
    int depth = word.length();
    int index;

    Node travel = root;

    for (; level < depth; level++) {
      index = word.charAt(level) - 'a';
      index = formatIndex(word.charAt(level));
      if (travel.children[index] == null) {
        return false;
      }
      travel = travel.children[index];
    }
    return (travel != null && travel.isWord);
  }

  /**
   * Delete a word from trie.
   *
   * @param root root of trie
   * @param word word to delete
   * @param depth current depth
   */
  public void delete(Node root, String word, int depth) {
    if (root == null) {
      return;
    }

    if (depth == word.length()) {
      if (root.isWord) {
        root.isWord = false;
      }
      if (!hasChild(root)) {
        root = null;
      }
      return;
    }

    int index = formatIndex(word.charAt(depth));
    delete(root.children[index], word, depth + 1);

    // Delete the node does have any child (got deleted) and not be the end of another word.
    if (hasChild(root) && root.isWord == false) {
      root = null;
    }
    return;
  }

  /**
   * Change a word into another word (if exists).
   *
   * @param oldWord old English word
   * @param newWord new English word
   */
  public void change(String oldWord, String newWord) {
    if (search(oldWord) == true) {
      delete(root, oldWord, 0);
      insert(newWord);
    }
  }

  private boolean hasChild(Node root) {
    for (int i = 0; i < NUM_OF_CHAR; i++) {
      if (root.children[i] != null) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get an ArrayList of recommendation words by its prefix.
   *
   * @param word prefix word
   * @return list of words which contain this prefix
   */
  public ArrayList<String> getRecommendation(String word) {
    ArrayList<String> rec = new ArrayList<String>();
    StringBuffer currentWord = new StringBuffer(); // same as String but mutable
    int level = 0;
    int depth = word.length();
    int index;
    Node travel = root;
    for (; level < depth; level++) {
      index = formatIndex(word.charAt(level));
      // handle non-english char
      // should cause ArrayIndexOutOfBound
      if (index < 0 || index > 29) {
        rec.clear();
        return rec;
      }
      if (travel.children[index] == null) {
        return rec;
      }
      currentWord.append(word.charAt(level));
      travel = travel.children[index];
    }
    getWord(travel, rec, currentWord);
    return rec;
  }

  /**
   * Recursive function for getRecommendation().
   *
   * @param root root of trie
   * @param rec the return ArrayList
   * @param currentWord StringBuffer to build word
   */
  private void getWord(Node root, ArrayList<String> rec, StringBuffer currentWord) {
    if (currentWord.isEmpty()) {
      return;
    }

    if (root.isWord) {
      rec.add(currentWord.toString());
    }

    if (!hasChild(root)) {
      return;
    }

    for (int i = 0; i < NUM_OF_CHAR; i++) {
      if (root.children[i] != null) {
        currentWord.append(intToChar(i));
        getWord(root.children[i], rec, currentWord);
        currentWord.setLength(currentWord.length() - 1);
      }
    }
  }

  /** Node of trie. */
  public class Node {
    boolean isWord; // true if this node is the end of an English word
    Node[] children = new Node[NUM_OF_CHAR];

    Node() {
      isWord = false;
      for (int i = 0; i < NUM_OF_CHAR; i++) {
        children[i] = null;
      }
    }
  }
}
