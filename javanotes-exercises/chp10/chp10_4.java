import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class chp10_4 {

  private static class Even implements Predicate<Integer> {
    public boolean test(Integer i) {
      if (i == null) return false;
      else if (i % 2 == 0) return true;
      else return false;
    }
  }
  private static class Odd implements Predicate<Integer> {
    public boolean test(Integer i) {
      if (i == null) return false;
      else if (i % 2 != 0) return true;
      else return false;
    }
  }

  private static Predicate<Integer> even;
  private static Predicate<Integer> odd;

  public static void main(String[] args) {
    even = new Even();
    odd = new Odd();
    ArrayList<Integer> list = new ArrayList<Integer>(100);
    addValues(list);

    System.out.println("\nPredicate Tester");
    System.out.println("\nA list of numbers, 1 - 100, is at your disposal.");
    showOptions();

    boolean stop = false;

    while (!stop) {
      System.out.print("\n? ");
      String input = TextIO.getln();
      switch (input) {
        case "see list":
          printList(list);
          break;
        case "remove even":
          Predicates.remove(list, even);
          System.out.println("Ok...");
          break;
        case "remove odd":
          Predicates.remove(list, odd);
          System.out.println("  Ok...");
          break;
        case "retain even":
          Predicates.retain(list, even);
          System.out.println("  Ok...");
          break;
        case "retain odd":
          Predicates.retain(list, odd);
          System.out.println("  Ok...");
          break;
        case "collect even":
          ArrayList<Integer> newEvenList = Predicates.collect(list, even);
          System.out.println("New List:");
          printList(newEvenList);
          break;
        case "collect odd":
          ArrayList<Integer> newOddList = Predicates.collect(list, odd);
          System.out.println("New List:");
          printList(newOddList);
          break;
        case "find":
          doFind(list);
          break;
        case "reset":
          addValues(list);
          System.out.println("  List reset");
          break;
        case "options":
          showOptions();
          break;
        case "quit":
          stop = true;
          break;
        default:
          System.out.println("  Command not found...");
          break;
      }
    }
    System.out.println("\nDone");
  }

  private static void showOptions() {
    System.out.println(" Type 'see list' to view the list at any time.");
    System.out.println(" Type 'remove even' or 'remove odd' to filter those values out.");
    System.out.println(" Type 'retain even' or 'retain odd' to filter non-matching values out.");
    System.out.println(" Type 'collect even' or 'collect odd' to make a new list with");
    System.out.println("   those values and print it out.");
    System.out.println(" Type 'find' to start a search for the index of particular values.");
    System.out.println(" Type 'reset' to restore the list to its default state.");
    System.out.println(" Type 'options' to view this list of commands at any time.");
    System.out.println(" Type 'quit' to end.");
  }

  private static void addValues(ArrayList<Integer> list) {
    list.clear();
    for (int i = 1; i <= 100; i++) {
      list.add(i);
    }
  }

  private static void printList(ArrayList<Integer> list) {
    if (list.size() == 0) {
      System.out.println("\nNothing to print");
      return;
    }
    System.out.println();
    System.out.printf("%3d ", list.get(0));
    int count = 1;
    int size = list.size();
    for (int i = 1; i < size; i++) {
      System.out.printf("%3d ", list.get(i));
      count++;
      if (count == 10) {
        System.out.println();
        count = 0;
      }
    }
  }

  private static void doFind(ArrayList<Integer> list) {
    while (true) {
      System.out.println("Enter 'even' or 'odd' to find the first number of that type ('quit' to end).");
      System.out.print("? ");
      String input = TextIO.getln().trim();
      int x = 0;

      if (input.equals("quit")) break;

      if (input.equals("even")) {
        x = Predicates.find(list, even);
      } else if (input.equals("odd")) {
        x = Predicates.find(list, odd);
      } else {
        System.out.println("Invalid input!");
      }

      if (x == -1) {
        System.out.println("  No match found");
      } else {
        System.out.println("  Found a match at position " + x);
      }
    }
  }

} // end class
