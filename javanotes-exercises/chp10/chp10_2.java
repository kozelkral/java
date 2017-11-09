import java.util.Set;
import java.util.TreeSet;

public class chp10_2 {

  private static TreeSet<Integer> set1;
  private static TreeSet<Integer> set2;
  private static char op;
  private static boolean reading;

  public static void main(String[] args) {
    set1 = new TreeSet<Integer>();
    set2 = new TreeSet<Integer>();

    System.out.println("\nSet Calculator");
    System.out.println("\nEnter two sets enclosed in [] and separated by commas.");
    System.out.println("The sets should have either '+', '*', or '-' between them to");
    System.out.println("signify respectively: union, intersection, or difference.");
    System.out.println("Enter an empty line to quit.");

    while (true) {
      TextIO.put("\nInput: ");
      reading = true;
      TextIO.skipBlanks();
      if (TextIO.peek() == '\n') break;
      TreeSet<Integer> result = calculate();
      if (result != null) {
        System.out.println("\nDone... writing output...");
        System.out.println("  Result: " + result);
      }
      if (reading) {
        TextIO.getln();
        reading = false;
      }
    }
    System.out.println("\nDone");
  } // end main

  private static TreeSet<Integer> calculate() {
    try {
      parseLine();
    } catch (ParseError e) {
      System.out.println("\n" + e.getMessage());
      return null;
    }
    TreeSet<Integer> copy = new TreeSet<Integer>(set1);
    switch (op) {
      case '+':
        copy.addAll(set2);
        return copy;
      case '*':
        copy.retainAll(set2);
        return copy;
      case '-':
        copy.removeAll(set2);
        return copy;
      default:
        return null;
    }
  }

  private static void parseLine() throws ParseError {
    set1 = parseSet();
    TextIO.skipBlanks();
    if (TextIO.peek() != '+' && TextIO.peek() != '*' && TextIO.peek() != '-') {
      throw new ParseError("Missing operator or bad operator");
    }
    op = TextIO.getChar();
    set2 = parseSet();
    TextIO.skipBlanks();
    if (TextIO.peek() != '\n') {
      System.out.println("\nDiscarding extra input: " + TextIO.getln());
      reading = false;
    }
  }

  private static TreeSet<Integer> parseSet() throws ParseError {
    TreeSet<Integer> set = new TreeSet<Integer>();
    TextIO.skipBlanks();
    if (TextIO.peek() != '[') {
      throw new ParseError("Missing start bracket");
    }
    TextIO.getChar();
    TextIO.skipBlanks();
    while (TextIO.peek() != ']') {
      if (!Character.isDigit(TextIO.peek())) {
        throw new ParseError("Number not found in set");
      }
      set.add(TextIO.getInt());
      TextIO.skipBlanks();
      if (TextIO.peek() == ',') {
        TextIO.getChar(); // read the comma
        TextIO.skipBlanks();
        if (TextIO.peek() == '\n' || TextIO.peek() == ']') {
          throw new ParseError("Missing data after comma");
        }
      } else if (TextIO.peek() == '\n') {
        throw new ParseError("Missing closing bracket");
      }
    }
    TextIO.getAnyChar(); // read the closing bracket
    return set;
  }

  private static class ParseError extends Exception {
    ParseError(String s) {
      super(s);
    }
  }

} // end class
