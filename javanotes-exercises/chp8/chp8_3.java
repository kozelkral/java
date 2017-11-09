import java.util.regex.Pattern;
import java.util.regex.Matcher;
// 7a 5r 1r 3i 6o 2a 4l
public class chp8_3 {
  // NUMBERS / Letters is used in toString to convert Arabic to Roman
  private static int[] NUMBERS = new int[]
    {1000,900,500,400,100,90,50,40,10,9,5,4,1};
  private static String[] NUMERALS = new String[]
    {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

  public static void main(String[] args) {
    int arabic; // used to store the arabic value of the input
    String roman; // used to store the roman value of the input
    boolean isArabic; // whether the user input an arabic or roman numeral
    String input; // initial user input before type is known

    do {
      System.out.println("Enter either an Arabic(123) or Roman(I II III) numeral:");

      // check whether user entered a number or string
      char ch = TextIO.peek();
      if (Character.isDigit(ch)) {
        isArabic = true;
      } else {
        isArabic = false;
      }
      // read the input
      input = TextIO.getln();

      try {
        if (isArabic) {
          arabic = validateInt(input); // will throw error if invalid
          roman = toString(arabic);
          System.out.println("The Roman equivalent is: " + roman);
        } else {
          roman = validateString(input); // will throw error if invalid
          arabic = toInt(roman);
          System.out.println("The Arabic equivalent is: " + arabic);
        }
      } catch (NumberFormatException e) {
        System.out.println("Your input was invalid!");
        System.out.println(e.getMessage());
      } finally {
        System.out.println();
        System.out.print("Do you wish to continue? (enter blank to quit) ");
        input = TextIO.getln();
      }

    } while (!input.equals(""));

  } // end main

  public static String toString(int n) {
    String s = "";
    for (int i = 0; i < NUMBERS.length; i++) {
      while (n >= NUMBERS[i]) {
        s += NUMERALS[i];
        n -= NUMBERS[i];
      }
    }
    if (n != 0) {
      throw new NumberFormatException("n doesn't equal 0");
    }
    return s.toUpperCase();
  } // end toString

  public static int toInt(String s) {
    int n = 0,
        pos = 0;
    String sub = "";
    s = s.toUpperCase();

    for (int i = 0; i < NUMERALS.length; i++) {
      if ((pos+2) <= s.length()) {
        if ((pos+2) == s.length()) {
          sub = s.substring(pos);
        } else {
          sub = s.substring(pos,pos+2);
        }
        for (int j = 0; j < NUMERALS.length; j++) {
          if (sub.equals(NUMERALS[j])) {
            n += NUMBERS[j]; // add combo value
            pos += 2; // skip next letter
            break;
          }
        }
      }

      if ((pos+1) >= s.length()) {
        sub = s.substring(pos);
      } else {
        sub = s.substring(pos, pos+1);
      }
      while (sub.equals(NUMERALS[i])) {
        n += NUMBERS[i];
        pos++;

        if ((pos+1) >= s.length()) {
          sub = s.substring(pos);
        } else {
          sub = s.substring(pos, pos+1);
        }
      }
    }
    return n;
  } // end toInt

  public static String validateString(String s) throws NumberFormatException {

    int startIndex = 0,
        endIndex = 0;
    String[] matchSet = new String[] {"m","d","c","l","x","v","i"};
    String matcher;
    s = s.toLowerCase();

    for (int i = 0; i < matchSet.length; i++) {
      matcher = matchSet[i];
      char ch = matcher.charAt(0);
      String sub = s.substring(startIndex);
      boolean isEnd = false;

      // check if two-letter combination exists
      if ((startIndex+1) <= s.length() && sub.length() >= 2) {
        char current, valid;
        boolean isValid;
        int offset = 0; // how much to adjust 'i' to compensate since ex: cmcc isn't possible
        current = sub.charAt(1);
        valid = sub.charAt(0);

        switch (current) {
          case 'm':
            offset = 2;
            isValid = (valid == 'c');
            break;
          case 'd':
            offset = 1;
            isValid = (valid == 'c');
            break;
          case 'c':
            offset = 2;
            isValid = (valid == 'x');
            break;
          case 'l':
            offset = 1;
            isValid = (valid == 'x');
            break;
          case 'x':
            offset = 2;
            isValid = (valid == 'i');
            break;
          case 'v':
            offset = 1;
            isValid = (valid == 'i');
            break;
          case 'i':
            isValid = false;
            break;
          default:
            throw new NumberFormatException("Something strange in the switch statement");
        }

        if (isValid) {
          startIndex += 2; // adjust startIndex to skip these characters next run
          i += offset;
          continue;
        }
      } // end if

      for (int x = 0; x < i; x++) {
        if (sub.indexOf(matchSet[x]) > -1) {
          throw new NumberFormatException("The numeral '" + matchSet[x].toUpperCase()
            + "' occurs where it shouldn't");
        }
      }

      if (s.indexOf(matcher) == -1) {
        // not present anywhere
        continue;
      }

      int index = startIndex;
      while (index < s.length() && ch == s.charAt(index)) {
        index++;
      }
      endIndex = index;

      if (endIndex == startIndex) { continue; } // no match on this character

      if (endIndex != s.length()) {
        sub = s.substring(startIndex, endIndex);
      }

      startIndex = endIndex;

      if (sub.length() > 3) {
        throw new NumberFormatException("Invalid Roman numeral: too many of one character!");
      }
    }
    return s;
  } // end validate String

  public static int validateInt(String s) throws NumberFormatException {
    int returnVal; // what will be returned if no error is thrown
    try {
      returnVal = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("The given number is not of type Integer!");
    }
    if (returnVal < 1 || returnVal > 3999) {
      throw new NumberFormatException("The given number is outside the legal range!");
    }
    return returnVal;
  } // end validate int

} // end class chp8_3
