import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chp11_3_client {

  public static void main(String[] args) {
    Socket connection;
    BufferedReader in;
    PrintWriter out;
    Scanner input = new Scanner(System.in);

    String address, portStr;
    int port = 3200;

    if (args.length == 0) {
      System.out.print("Enter remote address: ");
      address = input.nextLine().trim();
      System.out.print("Enter port: ");
      portStr = input.nextLine().trim();
    } else {
      address = args[0];
      if (args.length > 1) {
        portStr = args[1];
      } else {
        System.out.print("Enter port: ");
        portStr = input.nextLine().trim();
      }
    }

    try {
      port = Integer.parseInt(portStr);
      if (port < 1024 || port > 65535) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      System.out.println("Bad port number provided " + portStr);
    }

    try {
      connection = new Socket(address, port);
      in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      out = new PrintWriter(connection.getOutputStream());

      System.out.println("Connected");

      System.out.print("Command: ");
      String command = input.nextLine().trim();

      out.println(command);
      out.flush();
      if (out.checkError()) {
        System.out.println("An error occurred while sending the command");
        return;
      }

      String status = in.readLine();

      if (status.equals("PRINT")) {
        System.out.println("Ok\n");

        while (true) {
          String data = in.readLine();
          if (data == null) break;
          System.out.println("  " + data);
        }
        System.out.println("Done");

      } else if (status.equals("OK")) {
        try {
          File file = new File("chp11_from_server.txt");
          PrintWriter copy = new PrintWriter(new FileWriter(file));
          String line = in.readLine();
          while(line != null) {
            copy.println(line);
            line = in.readLine();
          }
          copy.flush();
          copy.close();
        } catch (IOException e) {
          System.out.println("Error reading file");
        }

      } else if (status.equals("ERROR")) {
        System.out.println(in.readLine());
      } else {
        System.out.println("Unexpected response received. Closing connection...");
      }

      in.close();
      out.close();
      input.close();
      connection.close();

    } catch (Exception err) {
      System.out.println("Something went wrong with the connection.");
      System.out.println("Ending... " + err.getMessage());
    }

  } // end main()

} // end class
