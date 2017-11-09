public class chp9_5 {

  public static void main(String[] args) {
    Node root = new Node();

    buildTree(root);
    System.out.println("\nTree built...");
    System.out.println("First value: " + root.value);
    System.out.println("Previous: " + root.previous.value + ", Next: " + root.next.value);

    int totalLeaves = countLeaves(root);
    System.out.println("Number of leaves: " + totalLeaves);
    int totalOfDepths = countLeafDepths(root, 0);
    System.out.println("Total of leaf depths: " + totalOfDepths);
    int max = maxDepth(root, 0);
    System.out.println("Max depth: " + max);
  } // end main

  /** Utility Subroutines **/
  private static void buildTree(Node root) {
    // Can do nothing with an empty root
    if (root == null) {
      throw new IllegalArgumentException("Parameter cannot be null");
    }
    // Build up tree
    for (int i = 0; i < 1023; i++) {
      root.insert(new Node());
    }
  } // end buildTree

  private static int countLeaves(Node node) {
    if (node == null) {
      // nothing
      return 0;
    } else if (node.previous == null && node.next == null) {
      // is a leaf
      return 1;
    } else {
      // find its leafy children
      return countLeaves(node.previous) + countLeaves(node.next);
    }
  } // end countLeaves

  private static int countLeafDepths(Node node, int depth) {
    if (node == null) {
      return 0;
    } else if (node.previous == null && node.next == null) {
      return depth;
    } else {
      return countLeafDepths(node.previous, depth+1) +
          countLeafDepths(node.next, depth+1);
    }
  } // end countDepths

  private static int maxDepth(Node node, int depth) {
    if (node == null) {
      return 0;
    } else if (node.previous == null && node.next == null) {
      return depth;
    } else {
      int maxPrev = maxDepth(node.previous, depth+1);
      int maxNext = maxDepth(node.next, depth+1);
      return Math.max(maxPrev,maxNext);
    }
  } // end maxDepth

  /** Nested Class Node **/
  private static class Node {
    int value;
    Node previous, next;

    Node() {
      this((int)(Math.random() * 10000));
    }
    Node(int x) {
      value = x;
    }

    void insert(Node newNode) {
      // Can do nothing if not given enough data to work with
      if (newNode == null) {
        throw new IllegalArgumentException("Parameter cannot be null");
      }
      // Start up a runner to move through the tree
      Node runner = this;
      // Move through the tree
      while (true) {
        // Special case if the values are equal
        if (newNode.value == runner.value) {
          if (runner.next != null) {
            Node temp = runner.next;
            runner.next = newNode;
            newNode.next = temp;
          } else {
            runner.next = newNode;
          }
          return;
        }
        // Move to the left of the tree if less
        if (newNode.value < runner.value) {
          if (runner.previous == null) {
            runner.previous = newNode;
            return;
          } else {
            runner = runner.previous;
          }
          // Move to the right of tree if more
        } else if (newNode.value > runner.value) {
          if (runner.next == null) {
            runner.next = newNode;
            return;
          } else {
            runner = runner.next;
          }
        }
      } // end while
    } // end insert

  } // end Node

} // end class
