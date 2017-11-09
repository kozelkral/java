
public class chp9_3 {

  public static void main(String[] args) {
    System.out.print("\nPress 'Enter' to build and print out a list of integers...");
    TextIO.getln();

    buildList(1,100);
    printList(root);

    System.out.println("\nList constructed");
    System.out.print("Press 'Enter' to reverse the list and print it...");
    TextIO.getln();

    Node reverse = reverseList(root);
    printList(reverse);

    System.out.println("\n...Done");
  } // end main

  /************************/
  /** Instance Variables **/
  /************************/
  private static Node root;

  /*************************/
  /** Utility Subroutines **/
  /*************************/
  private static void buildList(int start, int end) {
    // set the root
    root = new Node(start);
    // find which direction to go
    String dir = start > end ? "minus" : "plus";
    // set the start value for the while loop
    int x = dir.equals("plus") ? start + 1 : start - 1;
    // the runner object
    Node runner = root;

    if (dir.equals("plus")) {
      while (x <= end) {
        runner.next = new Node(x);
        runner = runner.next;
        x++;
      }
    } else {
      while (x >= end) {
        runner.next = new Node(x);
        runner = runner.next;
        x--;
      }
    }

  }

  private static Node reverseList(Node start) {
    // initialize a runner object
    Node runner = start;
    // initialize the return value
    Node rev = null;
    // set the runner to the last item in the list
    while (runner != null) {
      Node next = new Node(runner.value);
      next.next = rev;
      rev = next;
      runner = runner.next;
    }
    return rev;
  }

  private static void printList(Node start) {
    Node runner = start;
    while (runner != null) {
      System.out.print(runner.value + " ");
      runner = runner.next;
    }
  }

  /********************/
  /** Nested Classes **/
  /********************/
  private static class Node {
    int value;
    Node next;

    Node(int x) {
      value = x;
      next = null;
    }
  }

} // end class
