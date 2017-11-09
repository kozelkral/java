
public class chp8_1 {

  public static void main(String[] args) {
    double A, B, C; // user input
    double result; // if successful, the root returned from the equation
    String answer;

    System.out.println("Please enter a value for A to be used in the quadratic equation.");
    do {
      System.out.print("A = ");
      A = TextIO.getlnDouble();
      System.out.print("B = ");
      B = TextIO.getlnDouble();
      System.out.print("C = ");
      C = TextIO.getlnDouble();

      try {
        result = root(A,B,C);
        System.out.println("The equation was successful.");
        System.out.println("The resulting root is: " + result);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } finally {
        System.out.println();
        System.out.print("Continue? (enter blank to quit) ");
        answer = TextIO.getln();
      }

    } while (!answer.equals(""));

  } // end main

  /**
  * Returns the larger of the two roots of the quadratic equation
  * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
  * if the discriminant, B*B - 4*A*C, is negative, then an exception
  * of type IllegalArgumentException is thrown.
  */
  static public double root( double A, double B, double C )
                                throws IllegalArgumentException {
    if (A == 0) {
      throw new IllegalArgumentException("A can't be zero.");
    }
    else {
       double disc = B*B - 4*A*C;
       if (disc < 0)
          throw new IllegalArgumentException("Discriminant < zero.");
       return  (-B + Math.sqrt(disc)) / (2*A);
    }
  }

} // end class chp8.1

/*
Introduce program
do:
Ask for input
Try using input in subroutine
If an error occurs print that message
If program reaches here, everything is ok, print result
Ask to continue
while yes:
*/
