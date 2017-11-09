public class chp10_3 {
  // desired size of the array for the hash table
  private static final int SIZE = 100;
  private static HashTable table;

  /**
   * Nested class to represent a hash map, only valid for Strings
   */
  private static class HashTable {
    // Instance variables
    private static Node[] table;
    private static int size;
    private static int arraySize;

    HashTable(int size) {
      table = new Node[size];
      arraySize = size;
      this.size = 0;
    }

    /**
     * Given two parameters this creates a new Node out of them and adds it to
     * the hash table. If there is already a Node or list of Nodes at that location
     * it simply adds it to the end of the list.
     */
    void put(String k, String v) {
      try {
        Node node = new Node(k,v);
        int hashCode = Math.abs(k.hashCode()) % arraySize;
        if (table[hashCode] == null) {
          table[hashCode] = node;
        } else {
          Node runner = table[hashCode];
          while (runner.next != null) {
            runner = runner.next;
          }
          runner.next = node;
        }
        size++; // update size to mark this addition
      } catch (IllegalArgumentException e) {
        System.out.println("\n" + e.getMessage());
      }
    }

    /**
     * Gets and returns the node (or null) at the hash code location. The returned
     * node, if a list, is the root of the list.
     */
    Node get(String k) {
      int hashCode = Math.abs(k.hashCode()) % arraySize;
      return table[hashCode];
    }

    /**
     * Finds the given key in the hash table and, if there is anything there,
     * removes it and returns the root Node or null
     */
    Node remove(String k) {
      int hashCode = Math.abs(k.hashCode()) % arraySize;
      Node temp = table[hashCode];
      if (temp != null) {
        Node runner = temp;
        while (runner != null) {
          size--;
          runner = runner.next;
        }
        table[hashCode] = null;
      }
      return temp;
    }

    /**
     * Checks if a given key is already in the table
     */
    boolean containsKey(String k) {
      boolean contains = false;
      int hashCode = Math.abs(k.hashCode()) % arraySize;
      if (table[hashCode] != null) {
        contains = true;
      }
      return contains;
    }

    /**
     * The size of the hash table is defined by how many keys and values for each
     * key there are. This information is stored in the instance variable size.
     */
    int size() {
      return size;
    }
  }

  /**
   * Nested class to represent a node in the linked list used in the table
   */
  private static class Node {
    // Instance variables
    String key, value;
    Node next;

    Node(String key, String value) {
      if (key == null || value == null) {
        throw new IllegalArgumentException("Values of a node cannot be null");
      }
      this.key = key;
      this.value = value;
      next = null;
    }
  }

  public static void main(String[] args) {
    table = new HashTable(SIZE);

    System.out.println("\nCustom Hash Table");
    System.out.println("\nType 'add' to enter key : value pairs to the table.");
    System.out.println("Type 'see table' to view the current data in the table.");
    System.out.println("Type 'remove' to choose which key or keys to delete.");
    System.out.println("Type 'check' to test whether the table contains a particular item.");
    System.out.println("Type 'size' to view the current number of items in the table.");
    System.out.println("When done simply enter an empty line.");

    boolean stop = false;

    while (!stop) {
      System.out.print("\nCommand? ");
      String input = TextIO.getln().trim().toLowerCase();

      switch (input) {
        case "add":
          doAdd();
          break;
        case "see table":
          doShowTable();
          break;
        case "remove":
          if (table.size() > 0) doRemove();
          else System.out.println("Nothing in the table");
          break;
        case "check":
          doCheck();
          break;
        case "size":
          System.out.println("  Current size: " + table.size());
          break;
        case "":
          stop = true;
          break;
        default:
          System.out.println("  Command not found...");
          continue;
      }
    }
    System.out.println("\nDone.");
  } // end main

  static void doAdd() {
    System.out.println("Adding new values to table... Enter a blank space to end.");
    while (true) {
      System.out.print("Key? ");
      String k = TextIO.getln().trim();
      if (k.equals("")) break;

      System.out.print("Value? ");
      String v = TextIO.getln();
      if (v.equals("")) break;

      table.put(k,v);
      System.out.println("  Ok...");
    }
  }

  static void doRemove() {
    System.out.println("Removing values from table. If the table doesn't have the value");
    System.out.println("then nothing will be changed. Enter a blank space to end.");
    while (true) {
      System.out.print("\nKey to remove? ");
      String input = TextIO.getln().trim();
      if (input.equals("")) break;

      Node removed = table.remove(input);
      if (removed != null) {
        System.out.println("  Removed " + removed.key + " from the table");
      } else {
        System.out.println("  Key not found, nothing changed...");
      }

      if (table.size() == 0) {
        System.out.println("  Table empty, returning to main menu...");
        break;
      }
    }
  }

  static void doCheck() {
    System.out.println("Checking if the table contains a given key.");
    System.out.println("Enter a blank space to end.");
    while (true) {
      System.out.print("\nChecking for? ");
      String input = TextIO.getln().trim();
      if (input.equals("")) break;

      String message;
      if (table.containsKey(input)) {
        message = "was found in the table";
      } else {
        message = "was not found in the table";
      }

      System.out.println("  ( " + input + " ) " + message);
    }
  }

  static void doShowTable() {
    if (table.size() == 0) {
      System.out.println("  Nothing in the table");
    } else {
      for (Node n : table.table) {
        if (n == null) continue;
        System.out.println("\n" + n.key + ":");
        System.out.print("  " + n.value);

        if (n.next != null) {
          Node runner = n.next;
          while(runner != null) {
            System.out.print(", " + runner.value);
            runner = runner.next;
          }
        }

      }
    }
  }

} // end class
