import java.util.HashMap;

public class chp10_6 {

  private static HashMap<String,Object> symbolTable;
  private enum Functions {SIN, COS, TAN, ABS, SQRT, LOG}

  public static void main(String[] args) {
    System.out.println("In this simple program you may assign values to variables,");
    System.out.println("and print the results of expressions. Legal types are as follows:");
    System.out.println();
    printCommands();
    System.out.println();
    System.out.println("To view these available options again, simply enter 'commands'");
    System.out.println("To quit, simply enter an empty line.");

    symbolTable = new HashMap<String,Object>();
    symbolTable.put("pi", Math.PI);
    symbolTable.put("e", Math.E);
    symbolTable.put("sin", new StandardFunction(Functions.SIN));
    symbolTable.put("cos", new StandardFunction(Functions.COS));
    symbolTable.put("tan", new StandardFunction(Functions.TAN));
    symbolTable.put("abs", new StandardFunction(Functions.ABS));
    symbolTable.put("sqrt", new StandardFunction(Functions.SQRT));
    symbolTable.put("log", new StandardFunction(Functions.LOG));


    while (true) {
      System.out.print("\n? ");
      TextIO.skipBlanks();
      if (TextIO.peek() == '\n') break;
      try {
        String command = TextIO.getWord().toLowerCase();
        switch (command) {
          case "commands":
            printCommands();
            break;
          case "let":
            doLetCommand();
            break;
          case "print":
            doPrintCommand();
            break;
          default:
            System.out.println("Unknown command... Discarding input");
            break;
        }
        TextIO.getln();
      } catch (ParseError e) {
        System.out.println("Oops, there was an error!");
        System.out.println("Error: " + e.getMessage());
        TextIO.getln();
      }
    }
  } // end main

  private static void doLetCommand() throws ParseError {
    TextIO.skipBlanks();
    if (!Character.isLetter(TextIO.peek())) {
      throw new ParseError("Expected variable name after 'let'");
    }
    String var = readWord();
    TextIO.skipBlanks();
    if (TextIO.peek() != '=') {
      throw new ParseError("Missing '=' in let expression");
    }
    TextIO.getChar();
    double val = expressionValue();
    TextIO.skipBlanks();
    if (TextIO.peek() != '\n') {
      throw new ParseError("Unexpected data at the end of the expression");
    }
    symbolTable.put(var, val);
    System.out.println("  Ok...");
  } // end doLetCommand

  private static void doPrintCommand() throws ParseError {
    double val = expressionValue();
    TextIO.skipBlanks();
    if (TextIO.peek() != '\n') {
      throw new ParseError("Unexpected data at the end of the print command");
    }
    System.out.println("  Value: " + val);
  } // end doPrintCommand

  private static double expressionValue() throws ParseError {
    TextIO.skipBlanks();
    boolean negative = false;
    if (TextIO.peek() == '-') {
      negative = true;
      TextIO.getChar();
    }
    double val = termValue();
    if (negative) val = -val;
    TextIO.skipBlanks();
    while (TextIO.peek() == '+' || TextIO.peek() == '-') {
      char op = TextIO.getChar();
      double nextTerm = termValue();
      if (op == '+') {
        val += nextTerm;
      } else {
        val -= nextTerm;
      }
      TextIO.skipBlanks();
    }
    return val;
  } // end expressionValue

  private static double termValue() throws ParseError {
    TextIO.skipBlanks();
    double val = factorValue();
    TextIO.skipBlanks();
    while (TextIO.peek() == '*' || TextIO.peek() == '/') {
      char op = TextIO.getChar();
      double nextFactor = factorValue();
      if (op == '*') {
        val *= nextFactor;
      } else {
        val /= nextFactor;
      }
      TextIO.skipBlanks();
    }
    return val;
  } // end termValue

  private static double factorValue() throws ParseError {
    TextIO.skipBlanks();
    double val = primaryValue();
    TextIO.skipBlanks();
    while (TextIO.peek() == '^') {
      TextIO.getChar();
      double nextVal = primaryValue();
      val = Math.pow(val, nextVal);
      if (Double.isNaN(val)) {
        throw new ParseError("Illegal values given to '^' operator");
      }
      TextIO.skipBlanks();
    }
    return val;
  } // end factorValue

  private static double primaryValue() throws ParseError {
    TextIO.skipBlanks();
    char ch = TextIO.peek();
    if (Character.isDigit(ch)) {
      return TextIO.getDouble();
    } else if (Character.isLetter(ch)) {
      String name = readWord();
      if (symbolTable.get(name) == null) {
        throw new ParseError("Variable " + name + " not assigned a value");
      }
      if (symbolTable.get(name) instanceof Double) {
        Double val = (Double)symbolTable.get(name);
        return val.doubleValue();
      } else if (symbolTable.get(name) instanceof StandardFunction) {
        StandardFunction val = (StandardFunction)symbolTable.get(name);
        return val.evaluate(expressionValue());
      } else {
        throw new ParseError();
      }
    } else if (ch == '(') {
      TextIO.getChar();
      double val = expressionValue();
      TextIO.skipBlanks();
      if (TextIO.peek() != ')') {
        throw new ParseError("Missing closing parenthesis");
      }
      TextIO.getChar();
      return val;
    } else if (ch == '\n') {
      throw new ParseError("End of line found in the middle of the expression");
    } else if (ch == ')') {
      throw new ParseError("Extra right parenthesis found");
    } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
      throw new ParseError("Misplaced operator found");
    } else {
      throw new ParseError("Unexpected character " + ch + " found");
    }
  } // end primaryValue

  private static String readWord() {
    String word = "";
    while (Character.isLetter(TextIO.peek()) || Character.isDigit(TextIO.peek())) {
      word += TextIO.getChar();
    }
    return word;
  } // end readWord

  private static class StandardFunction {
    Functions function;

    StandardFunction(Functions code) {
      function = code;
    }

    double evaluate(double x) {
      switch (function) {
        case SIN:
          return Math.sin(x);
        case COS:
          return Math.cos(x);
        case TAN:
          return Math.tan(x);
        case ABS:
          return Math.abs(x);
        case SQRT:
          return Math.sqrt(x);
        case LOG:
          return Math.log(x);
        default:
          return Double.NaN;
      }
    }
  } // end StandardFunction

  private static class ParseError extends Exception {
    ParseError(String message) {
      super(message);
    }
    ParseError() {
      this("There was an error in parsing");
    }
  } // end nested class ParseError

  private static void printCommands() {
    System.out.println("  let (variable) = (value)");
    System.out.println("  print (expression)");
    System.out.println("  Valid operators:");
    System.out.println("    +, -, *, /");
    System.out.println("    sin, cos, tan, log, abs, sqrt");
  } // end printCommands

} // end class
