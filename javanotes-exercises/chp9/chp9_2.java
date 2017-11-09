public class chp9_2 {
  // instance variables
  private static Word root;

  public static void main(String[] args) {
    System.out.println();
    System.out.println("The number of different words in the file 'read.txt'");
    System.out.println("will be counted and printed out preceeded by a list of");
    System.out.println("all the words in the file written into 'write.txt'.");
    System.out.println("To change the output, simply change the contents of the file.");
    System.out.println("Press 'Enter' to begin...");

    String userInput = TextIO.getln();

    System.out.println("Reading...");
    readFile();
    System.out.println("Writing...");
    writeFile();
    System.out.println("There are " + countWords(root) + " words in the document 'read.txt'");
  } // end main

  /***********************/
  /** Utility Functions **/
  /***********************/

  public static void readFile() {
    TextIO.readFile("read.txt");
    String s = readNextWord();
    while (s != null) {
      s = s.toLowerCase();
      if (!contains(root, s)) {
        addWord(s);
      }
      s = readNextWord();
    }
  } // end readFile

  public static void writeFile() {
    if (root == null) {
      // nothing to write
      return;
    }

    TextIO.writeFile("write.txt");

    listAll(root);
  } // end writeFile

  private static void listAll(Word w) {
    if (w != null) {
      listAll(w.previous);
      TextIO.putln(w.getWord());
      listAll(w.next);
    }
  }

  private static void addWord(String word) {
    if (root == null) {
      root = new Word(word);
      return;
    } /*else if (word.compareTo(root.getWord()) < 0) {
      Word temp = root;
      root = new Word(word);
      temp.previous = root;
      root.next = temp;
      System.out.println(temp.previous.getWord());
      return;
    }*/
    Word runner = root;
    while (true) {
      if (word.compareTo(runner.getWord()) < 0) {
        if (runner.previous == null) {
          runner.previous = new Word(word);
          return;
        } else {
          runner = runner.previous;
        }
      } else {
        if (runner.next == null) {
          runner.next = new Word(word);
          return;
        } else {
          runner = runner.next;
        }
      }
    } // end while
  } // end addWord

  private static boolean contains(Word w, String word) {
    if (w == null) {
      // nothing further in tree
      return false;
    } else if (word.equals(w.getWord())) {
      // word already in list
      return true;
    } else if (word.compareTo(w.getWord()) < 0) {
      // word comes before this node
      return contains(w.previous, word);
    } else {
      // word comes after this node
      return contains(w.next, word);
    }
  } // end contains

  private static int countWords(Word w) {
    if (w == null) {
      return 0;
    } else {
      int previous = countWords(w.previous);
      int next = countWords(w.next);
      return 1 + previous + next;
    }
  } // end countWords

  private static String readNextWord() {
    char c = TextIO.peek();
    // first make sure the content is going to be letters
    while (!(c == TextIO.EOF) && !Character.isLetter(c)) {
      TextIO.getAnyChar();
      c = TextIO.peek();
    }
    if (c == TextIO.EOF) {
      return null;
    }
    // at this point the next character is a letter
    String word = "";
    while (true) {
      word += TextIO.getAnyChar();
      c = TextIO.peek();
      // check if word is a valid contraction
      if (c == '\'') {
        TextIO.getAnyChar();
        c = TextIO.peek();
        if (Character.isLetter(c)) {
          // is a valid contraction
          word += "\'" + TextIO.getAnyChar();
          c = TextIO.peek();
        } else {
          // apostrophe is not a valid part of the word
          break;
        }
      }
      if (!Character.isLetter(c)) {
        // word has ended
        break;
      }
      // if loop is still running, c is equal to the next letter at this point
    } // end while
    return word;
  } // end readNextWord

  /******************/
  /** Nested Class **/
  /******************/

  private static class Word {
    // instance variables
    private String word;
    private Word previous, next;

    Word(String w) {
      word = w;
    } // end constructor

    public void setWord(String s) {
      this.word = s;
    } // end setWord

    public String getWord() {
      return word;
    } // end getWord

    public String getPrevious() {
      if (previous == null) {
        return "";
      }
      return previous.getWord();
    } // end getPrevious

    public String getNext() {
      if (next == null) {
        return "";
      }
      return next.getWord();
    } // end getNext

    public Word previous() {
      return this.previous;
    } // end previous

    public Word next() {
      return this.next;
    } // end next
  } // end nested class



} // end class chp9_2
