import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.StringBuilder;

public class chp10_5 {
  private static TreeMap<String,TreeSet<Integer>> concordance;
  private static String word;
  private static TreeSet<Integer> pages;

  public static void main(String[] args) {
    concordance = new TreeMap<String, TreeSet<Integer>>();
    pages = new TreeSet<Integer>();
    int lineNumber = 1; // start on line 1

    System.out.println("\nPress 'enter' to select a file from which a concordance will be");
    System.out.println("constructed and written to another file.");
    // wait for the user to press Enter
    TextIO.getln();

    try {
      if (TextIO.readUserSelectedFile() == false) {
        System.out.println("No file selected. Exiting...");
        System.exit(1);
      }

      while (TextIO.peek() != TextIO.EOF) {
        // read the next word, also returns "\n"
        word = readWord();
        // increment lineNumber with every new line
        if (word.equals("\n")) {
          lineNumber++;
          // no further action necessary
          continue;
        }
        // Don't include words less than 3 characters long, ignore 'the'
        if (word.length() < 3 || word.toLowerCase().equals("the")) continue;
        word = formatWord(word);
        // add reference, or update existing reference
        updateReferences(word, lineNumber);
      }

      if (concordance.size() == 0) {
        System.out.println("Chosen file was empty. Exiting...");
        System.exit(1);
      }
      System.out.println("Ok...\nPress 'enter' to write the gathered data to the local file 'concordance.txt'");
      // wait for the user to press Enter
      TextIO.readStandardInput();
      TextIO.getln();
      TextIO.writeFile("concordance.txt");
      // call a subroutine to do the work
      writeConcordance();
      // notify of success
      System.out.println("Ok...Done");
    } catch (Exception e) {
      System.out.println("Sorry, an error occurred.");
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
    System.exit(0);
  } // end main

  private static String formatWord(String w) {
    String formatted = w.substring(0,1).toUpperCase() + w.substring(1).toLowerCase();
    return formatted;
  } // end formatWord

  private static void writeConcordance() {
    for (Map.Entry<String, TreeSet<Integer>> entry : concordance.entrySet()) {
      String term = entry.getKey();
      TreeSet<Integer> pageList = entry.getValue();
      TextIO.put(term + " (");
      for (int page : pageList) {
        TextIO.put(page + " ");
      }
      TextIO.putln(")");
    }
  } // end writeConcordance

  private static void updateReferences(String w, int n) {
    TreeSet<Integer> references;
    references = concordance.get(w);
    if (references == null) {
      // this is the first occurance
      references = new TreeSet<Integer>();
      references.add(n);
      concordance.put(w, references);
    } else {
      references.add(n);
    }
  } // end updateReferences

  private static String readWord() {
    // bypass blank spaces
    TextIO.skipBlanks();
    char next = TextIO.peek();
    StringBuilder buffer = new StringBuilder();
    // skip any non-letter characters
    while (next != TextIO.EOF && !Character.isLetter(next) && next != '\n') {
      TextIO.getAnyChar();
      next = TextIO.peek();
    }

    if (next == TextIO.EOF) {
      return null;
    }
    if (next == '\n') {
      TextIO.getAnyChar();
      return "\n";
    }

    while (true) {
      char current = TextIO.getAnyChar();
      next = TextIO.peek();

      buffer.append(current);

      if (next == '\'') {
        // read the apostrophe
        TextIO.getAnyChar();
        next = TextIO.peek();
        if (current == 's' && !Character.isLetter(next)) {
          buffer.append("\'");
        } else if (Character.isLetter(next)) {
          buffer.append("\'" + TextIO.getAnyChar());
        } else {
          break;
        }
        next = TextIO.peek();
      }

      if (!Character.isLetter(next)) break;
    }
    return buffer.toString();
  } // end readWord

} // end class
