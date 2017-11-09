import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class chp11_2 {

  public static void main(String[] args) {

    if (!(args.length > 0)) {
      System.out.println("No file(s) specified... exiting...");
    }

    ArrayList<File> files = new ArrayList<File>();
    for (int i = 0; i < args.length; i++) {
      files.add(new File(args[i]));
    }

    String indent = "  ";
    for (File f : files) {
      if (f.isDirectory()) {
        System.out.println(indent + f.getName() + " is a directory. No lines counted.");
      } else {
        try {
          int c = getLineCount(f);
          System.out.println(indent + f.getName() + " has " + c + " lines.");
        } catch (Exception e) {
          System.out.println(indent + "An error occurred trying to read " +
            f.getName() + ". Skipping file...");
        }
      }

    }
  } // end main()

  private static int getLineCount(File file) throws IOException {
    Scanner in = new Scanner(new FileReader(file));
    int count = 0;

    while (in.hasNext()) {
      in.nextLine();
      count++;
    }

    in.close();

    return count;
  } // end getLineCount()

} // end chp11_2
