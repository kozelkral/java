import java.math.BigInteger;

public class chp8_2 {

  public static void main(String[] args) {
    String input; // user input
    String answer; // whether the user wants to continue
    BigInteger inputVal; // user input will be converted into a BigInteger and stored here

    System.out.println("Welcome! This program runs a 3n + 1 calculation.");
    System.out.println("This version supports big integers, so there is no");
    System.out.println("limit to the size of it.");

    do {
      System.out.print("Your number? ");
      input = TextIO.getln();
      input = input.trim();
      try {
        inputVal = new BigInteger(input);
        calc3N(inputVal);
      } catch (NumberFormatException e) {
        System.out.println("You've entered an invalid integer!");
        System.out.println(e.getMessage());
      } finally {
        System.out.println();
        System.out.println("Would you like to continue? (Enter a blank line to quit)");
        answer = TextIO.getln();
      }
    } while (!answer.equals(""));
  } // end main

  public static void calc3N(BigInteger n) {
    int steps = 0; // track how many steps it takes to reach the final value
    // values for use in 3n+1 calculation
    BigInteger one = new BigInteger("1");
    BigInteger two = new BigInteger("2");
    BigInteger three = new BigInteger("3");

    System.out.println("Start: " + n.toString());
    while (!n.equals(one)) {
      if (n.testBit(0)) {
        n = n.multiply(three).add(one);
        steps++;
      } else {
        n = n.divide(two);
        steps++;
      }
      System.out.println(steps + " : " + n.toString());
    }
  } // end calc3N

} // end class

/**
Introduce program and functionality
do:
ask for starting number
collect input as string value
try
  create BigInteger out of input
  pass BigInteger to calc3N function
  return calculated result
  print out result
  print out steps required to reach it
catch NumberFormatException
  print error message
finally
  ask user whether to continue
  collect input from user
while user wishes to continue
*/
