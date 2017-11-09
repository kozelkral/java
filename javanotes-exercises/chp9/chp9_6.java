/*
    This program reads standard expressions typed in by the user.
    The program constructs an expression tree to represent the
    expression.  It then prints the value of the tree.  It also uses
    the tree to print out a list of commands that could be used
    on a stack machine to evaluate the expression.
    The expressions can use positive real numbers and
    the binary operators +, -, *, and /.  The unary minus operation
    is supported.  The expressions are defined by the BNF rules:

            <expression>  ::=  [ "-" ] <term> [ [ "+" | "-" ] <term> ]...

            <term>  ::=  <factor> [ [ "*" | "/" ] <factor> ]...

            <factor>  ::=  <number>  |  "(" <expression> ")"

*/

public class chp9_6 {

  /**
   *  Nested Abstract Class ExpressionNode
   *  This class represents the most basic concept of an expression and contains
   *  a value() method as well as a printStackCommands() method
   */
  abstract private static class ExpressionNode {
    abstract double value();
    abstract double value(double xVal);
    abstract ExpressionNode derivative();
    abstract void printInfix();
    abstract void printStackCommands();
  } // end ExpressionNode

  /**
   *  Nested Class ConstantNode
   *  This class represents a node that holds a number
   */
  private static class ConstantNode extends ExpressionNode {
    // instance variable
    double number;
    // constructor
    ConstantNode(double val) {
      number = val;
    }
    // define value() method
    double value() {
      return number;
    }
    // define value(xVal) method
    double value(double xVal) {
      return number;
    }
    // define derivative() method
    ExpressionNode derivative() {
      return new ConstantNode(0);
    }
    // define printInfix() method
    void printInfix() {
      System.out.print(number);
    }
    // define printStackCommands() method
    void printStackCommands() {
      System.out.println("  Push " + number);
    }
  } // end ConstantNode

  /**
   *  Nested Class VariableNode
   *  This class represents a node that is variable x
   */
  private static class VariableNode extends ConstantNode {
    // constructor
    VariableNode(double val) {
      super(val);
    }
    // define value(xVal) method
    double value(double xVal) {
      return xVal;
    }
    // define derivative() method
    ExpressionNode derivative() {
      return new ConstantNode(1);
    }
    // define printInfix() method
    void printInfix() {
      System.out.print("x");
    }
    // define printStackCommands() method
    void printStackCommands() {
      System.out.println("  Push x");
    }
  } // end VariableNode

  /**
   *  Nested Class BinaryOperatorNode
   *  This class represents an expression using a binary operator such as +, -,
   *  *, /.
   */
  private static class BinaryOperatorNode extends ExpressionNode {
    // instance variables
    char op;
    ExpressionNode left, right;
    // constructor
    BinaryOperatorNode(char op, ExpressionNode left, ExpressionNode right) {
      // make sure the parameters are valid
      assert op == '+' || op == '-' || op == '*' || op == '/';
      assert left != null && right != null;
      // store the values
      this.op = op;
      this.left = left;
      this.right = right;
    }
    // define value() method
    double value() {
      double x = left.value();
      double y = right.value();
      switch(op) {
        case '+': return x + y;
        case '-': return x - y;
        case '*': return x * y;
        case '/': return x / y;
        default: return Double.NaN;
      }
    }
    // define value(xVal) method
    double value(double xVal) {
      double x = left.value(xVal);
      double y = right.value(xVal);
      switch(op) {
        case '+': return x + y;
        case '-': return x - y;
        case '*': return x * y;
        case '/': return x / y;
        default: return Double.NaN;
      }
    }
    // define derivative() method
    ExpressionNode derivative() {
      ExpressionNode a = left;
      ExpressionNode b = right;
      ExpressionNode dA = left.derivative();
      ExpressionNode dB = right.derivative();
      switch(op) {
        case '+':
        case '-':
        // (da (+|-) dB)
        return new BinaryOperatorNode(op, dA, dB);
        case '*':
        // (a*dB + b*dA)
        return new BinaryOperatorNode('+',
          new BinaryOperatorNode(op, a, dB),
          new BinaryOperatorNode(op, b, dA));
        case '/':
        // (b*dA - a*db) / (b*b)
        return new BinaryOperatorNode(op,
          new BinaryOperatorNode('-',
            new BinaryOperatorNode('*', b, dA),
            new BinaryOperatorNode('*', a, dB)),
          new BinaryOperatorNode('*', b, b));
        default: return null;
      }
    }
    // define printInfix() method
    void printInfix() {
      System.out.print("(");
      left.printInfix();
      System.out.print(op);
      right.printInfix();
      System.out.print(")");
    }
    // define printStackCommands() method
    void printStackCommands() {
      left.printStackCommands();
      right.printStackCommands();
      System.out.println("  Operator " + op);
    }
  } // end BinaryOperatorNode

  /**
   *  Nested Class UnaryMinusNode
   *  This class represents a node that should be considered negative
   */
  private static class UnaryMinusNode extends ExpressionNode {
    // instance variable
    ExpressionNode operand;
    // constructor
    UnaryMinusNode(ExpressionNode operand) {
      assert operand != null;
      this.operand = operand;
    }
    // define value() method
    double value() {
      double val = operand.value();
      return -val;
    }
    // define value(xVal) method
    double value(double xVal) {
      double val = operand.value(xVal);
      return -val;
    }
    // define derivative() method
    ExpressionNode derivative() {
      return new UnaryMinusNode(operand.derivative());
    }
    // define printInfix() method
    void printInfix() {
      System.out.print("(-");
      operand.printInfix();
      System.out.print(")");
    }
    // define printStackCommands() method
    void printStackCommands() {
      operand.printStackCommands();
      System.out.println("  Unary Minus");
    }
  } // end UnaryMinusNode

  /** Custom Error ParseError **/
  private static class ParseError extends Exception {
    ParseError() {
      super("There was an error parsing");
    }
    ParseError(String message) {
      super(message);
    }
  } // end ParseError

  /* ---------------- End Nested Classes -------------------*/

  // Instance variable
  private static double x;
  private static boolean variableUsed;

  /** Main Subroutine **/
  public static void main(String[] args) {
    /* Chp 9_7 */
    while (true) {
      System.out.print("\nEnter an expression to be evaluated: ");
      TextIO.skipBlanks();
      if (TextIO.peek() == '\n') {
        break;
      }
      try {
        ExpressionNode expression = expressionTree();
        TextIO.skipBlanks();
        if (TextIO.peek() != '\n') {
          throw new ParseError("Unexpected data at end of line");
        }
        TextIO.getln();
        if (variableUsed) {
          ExpressionNode derivative = expression.derivative();
          System.out.println("\nValue for x = 0 is: " + expression.value());
          System.out.println("Value of derivative: " + derivative.value());
          System.out.println("Postfix evaulation:\n");
          derivative.printStackCommands();
          expression.printInfix();
          derivative.printInfix();
        } else {
          System.out.println("\nValue: " + expression.value());
          expression.printInfix();
        }
      } catch (ParseError error) {
        System.out.println("\nError in input: " + error.getMessage());
        System.out.println("Discarding input: " + TextIO.getln());
      } finally {
        variableUsed = false;
      }
    }

    /* Chp 9_6
    while (true) {
      System.out.print("\nEnter an expression to be evaluated: ");
      TextIO.skipBlanks();
      if (TextIO.peek() == '\n') {
        break;
      }
      try {
        ExpressionNode expression = expressionTree();
        TextIO.skipBlanks();
        if (TextIO.peek() != '\n') {
          throw new ParseError("Unexpected data at end of line");
        }
        TextIO.getln();
        if (variableUsed) {
          System.out.println("\nValue for x = 0 is: " + expression.value());
          System.out.println("\nValue for x = 1 is: " + expression.value(1));
          System.out.println("\nValue for x = 2 is: " + expression.value(2));
          System.out.println("\nValue for x = 3 is: " + expression.value(3));
          System.out.println("\nValue for x = 4 is: " + expression.value(4));
          System.out.println("\nValue for x = 5 is: " + expression.value(5));
          System.out.println("Postfix evaulation:\n");
          expression.printStackCommands();
        } else {
          System.out.println("\nValue is: " + expression.value());
          System.out.println("Postfix evaulation:\n");
          expression.printStackCommands();
        }
      } catch (ParseError error) {
        System.out.println("\nError in input: " + error.getMessage());
        System.out.println("Discarding input: " + TextIO.getln());
      } finally {
        variableUsed = false;
      }
    }*/
  } // end main

  /** Utility Subroutines **/

  /**
   *  Builds and expression tree from the current line of input
   *  @return an ExpressionNode that points at the root of the expression tree
   *  @throws ParseError if a syntax error is found, thrown in factorTree()
   */
  private static ExpressionNode expressionTree() throws ParseError {
    TextIO.skipBlanks();
    boolean negative = false;
    if (TextIO.peek() == '-') {
      TextIO.getAnyChar();
      negative = true;
    }
    ExpressionNode expression = termTree();
    if (negative) {
      expression = new UnaryMinusNode(expression);
    }
    TextIO.skipBlanks();
    while (TextIO.peek() == '+' || TextIO.peek() == '-') {
      char op = TextIO.getAnyChar();
      ExpressionNode nextTerm = termTree();
      expression = new BinaryOperatorNode(op, expression, nextTerm);
      TextIO.skipBlanks();
    }
    return expression;
  } // end expressionTree()

  /**
   *  Builds an expression tree from a term in the current line of input
   *  @return ExpressionNode that points to the root of the expression tree
   *  @throws ParseError if a syntax error is found, thrown in factorTree()
   */
  private static ExpressionNode termTree() throws ParseError {
    TextIO.skipBlanks();
    ExpressionNode term = factorTree();
    TextIO.skipBlanks();
    while (TextIO.peek() == '*' || TextIO.peek() == '/') {
      char op = TextIO.getAnyChar();
      ExpressionNode nextTerm = factorTree();
      term = new BinaryOperatorNode(op, term, nextTerm);
      TextIO.skipBlanks();
    }
    return term;
  } // end termTree()

  /**
   *  Builds an expression tree from a factor in the current line of input
   *  @return ExpressionNode that points to the root of the expression tree
   *  @throws ParseError if a syntax error is found
   */
  private static ExpressionNode factorTree() throws ParseError {
    char ch = TextIO.peek();
    if (Character.isDigit(ch)) {
      double number = TextIO.getDouble();
      return new ConstantNode(number);
    } else if (ch == 'x' || ch == 'X') {
      TextIO.getAnyChar();
      variableUsed = true;
      return new VariableNode(x);
    } else if (ch == '(') {
      TextIO.getAnyChar();
      ExpressionNode expression = expressionTree();
      TextIO.skipBlanks();
      if (TextIO.peek() != ')') {
        throw new ParseError("Missing closing parenthesis");
      }
      TextIO.getAnyChar();
      return expression;
    } else if (ch == '\n') {
      throw new ParseError("End of line found in the middle of expression");
    } else if (ch == ')') {
      throw new ParseError("Unexpected additional parenthesis found");
    } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
      throw new ParseError("Misplaced operator found");
    } else {
      throw new ParseError("Unexpected character \"" + ch + "\" found");
    }
  } // end factorTree()

} // end class
