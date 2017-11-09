public class chp9_4 {

  public static void main(String[] args) {
    root = new TreeNode();
    queue = new Queue();

    buildTree(root, 5);

    printQueue(root);
  } // end main

  /** Instance Variables **/
  private static Queue queue;
  private static TreeNode root;
  private static int count = 1;

  /** Subroutines **/
  private static void buildTree(TreeNode start, int size) {
    if (count == size) {
      return;
    }
    TreeNode runner = start;
    runner.left = new TreeNode();
    runner.right = new TreeNode();
    count++;
    buildTree(runner.left, size);
    buildTree(runner.right, size);
  }

  private static void printQueue(TreeNode start) {
    queue.enqueue(start);

    while (!queue.isEmpty()) {
      TreeNode node = queue.dequeue();
      System.out.println(node.value);
      if (node.left != null) {
        System.out.print("left " + node.left.value);
        queue.enqueue(node.left);
      }
      if (node.right != null) {
        System.out.println(", right " + node.right.value);
        queue.enqueue(node.right);
      }
    }
  }

  /** Nested Class TreeNode **/
  private static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode() {
      this((int)(Math.random() * 1000));
    }

    TreeNode(int x) {
      value = x;
    }
  } // end TreeNode

  /** Nested Class Queue **/
  private static class Queue {
    Node front;
    Node back;

    private static class Node {
      TreeNode item;
      Node next;

      Node(TreeNode node) {
        item = node;
      }
    }

    void enqueue(TreeNode node) {
      Node newNode = new Node(node);
      if (this.isEmpty()) {
        front = newNode;
        back = front;
      } else {
        back.next = newNode;
        back = newNode;
      }
    }

    TreeNode dequeue() {
      if (this.isEmpty()) {
        return null;
      }
      TreeNode node = front.item;
      front = front.next;
      if (front== null) {
        back = null;
      }
      return node;
    }

    boolean isEmpty() {
      return (front == null);
    }
  } // end Queue

} // end class
