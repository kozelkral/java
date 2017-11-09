import java.math.BigInteger;

public class chp9_1 {

  public static void main(String[] args) {
    String userInput,
    errorMessage = "Your input was incorrect! ";
    fibonacci fib;
    factorial fac;

    System.out.println();
    System.out.println("Enter either function fibonacci(n) or factorial(n) to see");
    System.out.println("the computed result. 'n' must be a positive integer, otherwise");
    System.out.println("you will be asked to re-enter.");
    System.out.println("Simply press 'Enter' to quit.");

    userInput = getInput();
    while (!userInput.equals("")) {
      try {
        fac = new factorial(userInput);
      } catch (IllegalArgumentException err) {
        // not a factorial, try fibonacci
        try {
          fib = new fibonacci(userInput);
        } catch(IllegalArgumentException e) {
          System.out.println("Bad input!");
        }
      } finally {
        userInput = getInput();
      }
    }

    System.out.println();
    System.out.println("Goodbye");
  } // end main

  // Nested class fibonacci
  private static class fibonacci {
    private BigInteger num;
    private BigInteger zero = new BigInteger("0");
    private BigInteger one = new BigInteger("1");
    private BigInteger two = new BigInteger("2");

    fibonacci(String input) {
      try {
        parseInput(input);
        BigInteger solution = solve(num);
        System.out.println("fibonacci(" + num.toString() + ") = " + solution.toString());
      } catch (ParseError e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    } // end constructor

    private void parseInput(String s) throws ParseError {
      String paramString = s.trim();
      int length = s.length();
      // check for first parenthesis
      int pIndex = s.indexOf("(");
      if (pIndex == -1) {
        throw new ParseError("Missing start parenthesis");
      }
      // make sure the phrase ends with a parenthesis
      if (s.charAt(length-1) != ')') {
        throw new ParseError("Missing ending parenthesis");
      }
      // check that the given function is fibonacci
      String name = s.substring(0, pIndex).toLowerCase();
      if(!name.equals("fibonacci")) {
        throw new ParseError("Wrong function type: " + name);
      }
      // attempt to parse numbers
      String temp = s.substring(pIndex+1, length-1);
      try {
        num = new BigInteger(temp);
      } catch (NumberFormatException e) {
        throw new ParseError(e.getMessage());
      }
      // at this point parsing worked
    } // end parseInput

    private BigInteger solve(BigInteger n) {
      if (n.equals(zero)) {
        return one;
      }
      if (n.equals(one)) {
        return one;
      }

      BigInteger nMinusOne = n.subtract(one);
      BigInteger nMinusTwo = n.subtract(two);
      BigInteger temp = solve(nMinusOne).add(solve(nMinusTwo));
      return temp;

    } // end solve

  } // end nested class fibonacci

  // Nested class factorial
  private static class factorial {
    private BigInteger num;
    private BigInteger zero = new BigInteger("0");
    private BigInteger one = new BigInteger("1");

    factorial(String input) {
      try {
        parseInput(input);
        BigInteger solution = solve(num);
        System.out.println("factorial(" + num.toString() + ") = " + solution.toString());
      } catch (ParseError e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    } // end constructor

    private void parseInput(String s) throws ParseError {
      String paramString = s.trim();
      int length = s.length();
      // check for first parenthesis
      int pIndex = s.indexOf("(");
      if (pIndex == -1) {
        throw new ParseError("Missing start parenthesis");
      }
      // make sure the phrase ends with a parenthesis
      if (s.charAt(length-1) != ')') {
        throw new ParseError("Missing ending parenthesis");
      }
      // check that the given function is factorial
      String name = s.substring(0, pIndex).toLowerCase();
      if(!name.equals("factorial")) {
        throw new ParseError("Wrong function type: " + name);
      }
      // attempt to parse numbers
      String temp = s.substring(pIndex+1, length-1);
      try {
        num = new BigInteger(temp);
      } catch (NumberFormatException e) {
        throw new ParseError(e.getMessage());
      }
      // at this point parsing worked
    } // end parseInput

    private BigInteger solve(BigInteger n) {
      if (n.equals(zero)) {
        return one;
      }
      BigInteger temp = n.multiply(solve(n.subtract(one)));
      return temp;
    } // end solve

  } // end nested class factorial

  /******************************/
  /***** UTILITY FUNCTIONS ******/
  /******************************/

  private static String getInput() {
    System.out.println();
    System.out.print("? ");
    String input = TextIO.getln();
    return input;
  }

  private static class ParseError extends RuntimeException {
    private ParseError(String message) {
      super(message);
    }
  }

} // end main class
