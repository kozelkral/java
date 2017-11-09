import java.io.*;
import java.util.Scanner;
import java.util.Date;

public class cmd {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in); // for reading user input
    String input; // store the information obtained from Scanner in
    boolean stop = false; // ends while loop ending program
    File directory; // for navigating and listing

    System.out.println("\nTo view possible commands enter 'commands'.");

    while (!stop) {
      directory = new File(System.getProperty("user.dir"));
      // wait for user input
      System.out.print("\n" + directory + " _");
      // grab a line from the user input
      input = in.nextLine().trim();
      // split input into tokens
      String[] tokens = input.split(" ");
      // grab the first token, which should be the entered command
      String command = tokens[0];
      // take the appropriate action based on the variable 'input'
      switch (command) {
        case "cd":
          break;
        case "ls":
          if (tokens.length > 1) {
            discard(tokens, 1);
          }
          listFiles(directory);
          break;
        case "mkdir":
          if (tokens.length < 2) {
            System.out.println("  Command 'mkdir' missing parameter. Discarding...");
            System.out.println("  File not written");
            break;
          } else if (tokens.length == 3) {
            if (!(tokens[1].equals("-f"))) {
              discard(tokens, 2);
              System.out.println("  File not written");
            } else {
              makeFile(tokens[2], true);
            }
          } else if (tokens.length > 3) {
            discard(tokens, 3);
            System.out.println("  File not written");
          } else {
            makeFile(tokens[1], false);
          }
          break;
        case "rmdir":
          break;
        case "copy":
          break;
        case "commands":
          if (tokens.length > 1) {
            discard(tokens, 1);
          }
          printCommands();
          break;
        case "q":
        case "quit":
          stop = true;
          break;
        default:
          System.out.println("\nCommand not recognized. To see available commands, enter 'commands'.");
          break;
      } // end switch

    } // end while

  } // end main

  private static void listFiles(File dir) {
    String[] files = dir.list();
    int count = 0;
    System.out.println("\nFiles in current directory: ");
    for (String s : files) {
      count++;
      System.out.print("  " + s);
      if (count >= 10) {
        count = 0;
        System.out.println();
      }
    }
    System.out.println();
  } // end listFiles()

  private static File makeFile(String name, boolean force) {
    File file = new File(name);
    if (file.exists() && !force) {
      System.out.println("File " + file + " already exists, please choose a new name or use the option -f.");
      return null;
    }
    Date now = new Date();
    String message = "/* File created at " + now + " */";
    try (PrintWriter out = new PrintWriter(new FileOutputStream(name)) ) {
      out.println(message);
      out.flush();
      if (out.checkError()) {
        throw new IOException("Error occurred while trying to write file.");
      }
    } catch (Exception e) {
      System.out.println("An error occurred while making the file " + file + "\n  " + e.getMessage());
    }
    System.out.println("  File written");
    return file;
  }

  private static void printCommands() {
    System.out.println("\nAvailable commands:");
    System.out.println("  cd (not yet supported)");
    System.out.println("  ls - show files in current directory");
    System.out.println("  mkdir (-f) - make a new file, -f will overwrite already existing files");
    System.out.println("  rmdir (not yet supported)");
    System.out.println("  copy (not yet supported)");
    System.out.println("  commands - shows possible commands");
    System.out.println("  q or quit - exits application");
  } // end printCommands()

  private static void discard(String[] tokens, int start) {
    System.out.print("\nDiscarding unexpected input: ");
    for (int i = start; i < tokens.length; i++) {
      System.out.print(tokens[i] + " ");
    }
  } // end discard

} // end class
