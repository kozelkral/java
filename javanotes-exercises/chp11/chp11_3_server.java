import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chp11_3_server {

  static final int DEFAULT_PORT = 32000;
  static final File DEFAULT_DIR = new File(System.getProperty("user.dir"));

  public static void main(String[] args) {
    int port = DEFAULT_PORT;
    File dir = DEFAULT_DIR;
    ServerSocket listener;

    if (args.length == 2) {
      dir = new File(args[0]);
      try {
        port = Integer.parseInt(args[1]);
        if (port < 0 || port > 65535)
          throw new NumberFormatException("Port must be between 0 and 65535.");
      } catch (NumberFormatException e) {
        System.out.println("Bad port: " + e.getMessage());
        System.exit(1);
      }
    } else if (args.length == 1) {
      dir = new File (args[0]);
    }

    if (!dir.isDirectory() || dir == null) {
      System.out.println(dir.toString() + " is not a directory");
      System.exit(1);
    }

    try {
      listener = new ServerSocket(port);
      System.out.println("Listening on port " + listener.getLocalPort());
      System.out.println("Directory is " + dir.getName());
      while (true) {
        System.out.println("Waiting for request...");
        Socket connection = listener.accept();
        System.out.println("Connected to " + connection.getInetAddress());
        try {
          serveFiles(connection, dir);
        } catch (Exception err) {
          System.out.println("An error occurred during the connection.");
          System.out.println("Connection closed.");
        }
      }

    } catch (Exception e) {
      System.out.println("Something went wrong with the server. It has shut down.");
      System.out.println(e.getMessage());
      System.exit(-1);
    }

  } // end main()

  private static void serveFiles(Socket connection, File dir) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    PrintWriter out = new PrintWriter(connection.getOutputStream());
    String[] files = dir.list();

    System.out.println("Waiting...");

    String[] msg = in.readLine().trim().split(" ");
    String command = msg[0].toUpperCase();
    System.out.println(command);

    switch (command) {
      case "INDEX":
        System.out.println("Received request: " + msg[0]);
        System.out.println("Sending...");
        out.println("PRINT");
        for (String s : files) {
          out.println(s);
        }
        out.flush();
        System.out.println("Done");
        break;
      case "GET":
        System.out.println("Received request: " + msg[0]);
        try {
          File file = new File(dir, msg[1].toLowerCase());
          System.out.println("Sending file...");
          out.println("OK");
          Scanner fileContent = new Scanner(new FileReader(file));
          while (fileContent.hasNextLine()) {
            String line = fileContent.nextLine();
            System.out.println(line);
            out.println(line);
          }
        } catch (Exception e) {
          System.out.println("Error retrieving file.");
          out.println("ERROR");
          out.println("File could not be found.");
        } finally {
          out.flush();
          System.out.println("Done");
          break;
        }
      default:
        out.println("ERROR");
        out.println("Command not recognized.");
        out.flush();
        break;
    }

    in.close();
    out.close();
    connection.close();

  } // end serveFiles()

} // end class
