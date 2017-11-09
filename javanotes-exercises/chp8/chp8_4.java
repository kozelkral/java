
public class chp8_4 {

  public static void main(String[] args) {
    // Initial instructions to user
    System.out.println();
    System.out.println("Enter an express you would like to use in some calculations");
    System.out.println("and press 'Enter'. After this you will be prompted to enter");
    System.out.println("a value for 'x' in the expression.");
    System.out.println("Example expression: '(x + 2) * (x / 3)'");

    // Gather input expression from user
    String userInput;
    Expr expr;

    System.out.println();
    System.out.println("Enter the expression you wish to use ('quit' or 'q' to end) -->");
    userInput = TextIO.getln().trim();

    while (!userInput.toUpperCase().equals("QUIT") && !userInput.toUpperCase().equals("Q")) {
      try {
        expr = new Expr(userInput);
        readVariableValues(expr);
        System.out.println();
        System.out.println("Enter a new expression ('quit' or 'q' to end) -->");
      } catch (IllegalArgumentException e) {
        System.out.println();
        System.out.println(e.getMessage());
        System.out.println("Please re-enter the expression using the correct format ('quit' or 'q' to end) -->");
      } finally {
        userInput = TextIO.getln().trim();
      }
    }

  } // end main

  public static void readVariableValues(Expr expr) {
    String userInput;
    double x, result;

    System.out.println();
    System.out.print("Enter a value for 'x' ('q' to quit): ");
    userInput = TextIO.getln().trim();

    while (!userInput.equals("q")) {
      try {
        x = Double.parseDouble(userInput);
        result = expr.value(x);

        if (Double.isNaN(result)) {
          System.out.println();
          System.out.println("With the given value 'x' the expression could not be completed.");
        } else {
          System.out.println();
          System.out.println("With the given value '" + x + "' the result came out to be '" + result + "'");
        }
      } catch (NumberFormatException e) {
        System.out.println();
        System.out.println("The value you entered was not a number!");
      } finally {
        System.out.print("Enter a new value ('q' to quit): ");
        userInput = TextIO.getln().trim();
      }
    }

  } // end readVariableValues

} // end class chp8_4
