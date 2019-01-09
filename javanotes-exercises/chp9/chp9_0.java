import java.util.Scanner;
import java.util.Date;
import static java.lang.System.out;

public class chp9_0 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] array = buildArray();

        out.println();
        out.println("Enter an integer value to be searched for in an array of up to 100000\n" +
        "and it's index will be returned:\n");

        int input = in.nextInt();

        Date start = new Date();

        int result = linearSearch(array, input);

        Date end = new Date();

        out.println("\nResult: " + (result == -1 ? "number not in range" : result));
        out.println("Search Time: " + (end.getTime() - start.getTime()) + "ms\n");
    }

    static int binarySearch(int[] A, int loIndex, int hiIndex, int val) {
        if (loIndex > hiIndex) {
            return -1;
        } else {
            int middle = (loIndex + hiIndex) / 2;
            if (val == A[middle])
                return middle;
            else if (val < A[middle])
                return binarySearch(A, loIndex, middle - 1, val);
            else
                return binarySearch(A, middle + 1, hiIndex, val);
        }
    } // end binarySearch()

    static int linearSearch(int[] A, int val) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == val) {
                return i;
            }
        }
        return -1;
    }

    static int[] buildArray() {
        int[] array = new int[100000];
        int value = 1;

        for (int i = 0; i < array.length; i++) {
            array[i] = value;
            value++;
        }

        return array;
    } // end buildArray()

} // end chap9_0
