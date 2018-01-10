import java.io.*;
import java.util.Scanner;
import java.util.Date;

public class cmd {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in); // for reading user input
    String input; // store the information obtained from Scanner in
    boolean stop = false; // ends while loop ending program
    File directory; // for navigating and listing
    String mod = ""; // for changing directory

    System.out.println("\nTo view possible commands enter 'commands'.");

    while (!stop) {
      if (mod.length() > 0) {
        directory = new File(System.getProperty("user.dir"), mod);
      } else {
        directory = new File(System.getProperty("user.dir"));
      }
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
          if (tokens.length < 2) {
            System.out.println("  Command 'cd' missing paramter <path name>. Discarding...");
          } else {
            if (tokens.length > 2) {
              discard(tokens, 2);
            }
            mod = tokens[1];
            if (mod.equals("~")) {
              mod = "";
            }
          }
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
          if (tokens.length < 2) {
            System.out.println("  Command 'rmdir' missing parameter <filename>. Discarding...");
            System.out.println("  File not deleted");
            break;
          } else {
            if (tokens.length > 2) {
              discard(tokens, 2);
            }
            File toDelete = new File(tokens[1]);
            if (!toDelete.exists()) {
              System.out.println("  File does not exist.");
              break;
            }
            boolean success = toDelete.delete();
            if (success) {
              System.out.println("  File deleted");
            } else {
              System.out.println("  File not deleted");
            }
          }
          break;
        case "copy":
          if (tokens.length < 3) {
            System.out.println("  Command 'copy' missing parameter(s). Discarding...");
            break;
          } else {
            if (tokens.length > 4) {
              discard(tokens, 4);
            }
            if (tokens[1].equals("-f")) {
              copyFile(tokens[2], tokens[3], true);
            } else {
              copyFile(tokens[1], tokens[2], false);
            }
          }
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

    if (files == null) {
      System.out.println("  Invalid directory");
      return;
    }

    int count = 0;
    System.out.println("\nFiles in current directory: ");
    for (String s : files) {
      count++;
      System.out.print("  " + s);
      if (count >= 5) {
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
    String message = "/* File created on " + now + " */";
    try (PrintWriter out = new PrintWriter(new FileOutputStream(name)) ) {
      out.println(message);
      out.flush();
      if (out.checkError()) {
        throw new IOException("Error occurred while trying to write file.");
      }
    } catch (Exception e) {
      System.out.println("An error occurred while making the file " + file + "\n  " + e.getMessage());
      return null;
    }
    System.out.println("  File written");
    return file;
  }

  private static File copyFile(String file1, String file2, boolean force) {
    File source = new File(file1);
    File copy = new File(file2);
    int byteCount = 0;

    if (!source.exists()) {
      System.out.println("  File " + file1 + " does not exist.");
      return null;
    }

    if (copy.exists() && !force) {
      System.out.println("  File " + file2 + " already exists. Use -f to overwrite existing files.");
      return null;
    }

    try (InputStream src = new FileInputStream(file1);
        OutputStream out = new FileOutputStream(file2)) {
      while (true) {
        int data = src.read();
        if (data < 0) break;
        out.write(data);
        byteCount++;
      }
    } catch (FileNotFoundException e) {
      System.out.println("  Error opening file: " + e);
      return null;
    } catch (Exception e) {
      System.out.println("  Error while copying file: " + e);
      return null;
    }

    System.out.println("  File " + file1 + " copied to " + file2 + " successfully.\n    " + byteCount + " bytes total.");
    return copy;
  }

  private static void printCommands() {
    System.out.println("\nAvailable commands:");
    System.out.println("  cd <directory> - changes directory path, '~' returns to working directory");
    System.out.println("  ls - show files in current directory");
    System.out.println("  mkdir (-f) <file name>, -f will overwrite existing files");
    System.out.println("  rmdir <file name> - remove chosen file");
    System.out.println("  copy (-f) <source file> <new file>, -f will overwrite existing files");
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
