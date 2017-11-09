import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;

public class chp10_1 {

  private static class Directory {
    TreeMap<String,String> directory;

    Directory() {
      directory = new TreeMap<String,String>();
    }
    void addEntry(String name, String number) {
      directory.put(name, number);
    }
    String getNumber(String name) {
      return directory.get(name);
    }
    void printDirectory() {
      Set<String> keys = directory.keySet();
      Iterator<String> iterator = keys.iterator();

      System.out.println("\nYour listings...");
      while (iterator.hasNext()) {
        String name = iterator.next();
        String number = directory.get(name);
        System.out.println("  " + name + ": " + number);
      }
    }
  }

  public static void main(String[] args) {
    Directory directory = new Directory();
    String name, number;

    System.out.println("\nPhone Directory");
    System.out.println("\nEnter Data (or nothing to stop entry)...");
    while (true) {
      System.out.print("Name: ");
      name = TextIO.getln();
      if (name.equals("")) break;
      System.out.print("Number: ");
      number = TextIO.getln();
      if (number.equals("")) break;
      try {
        directory.addEntry(name, number);
      } catch (Exception e) {
        System.out.println("\n" + e.getMessage() + "\n");
      }
      System.out.println("Ok...");
    }

    directory.printDirectory();
  }



} // end class
