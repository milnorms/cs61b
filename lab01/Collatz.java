/** Class that prints the Collatz sequence starting from a given number.
 *  @author Mili
 */
public class Collatz {

    /** Returns the nextNumber in a Collatz sequence.
     * @param n Number to run collatz sequence on
     * */
    public static int nextNumber(int n) {

        if (n==1) {
            return 1;
        }
        else if (n % 2 == 0){
            return n/2;
        } else {
            return (3*n)+1;
        }

    }

    public static void main(String[] args) {
        int n = 67;
        System.out.print(n + " ");

        // Some starter code to test
        while (n != 1) {          
            n = nextNumber(n);          
            System.out.print(n + " ");
        }
        System.out.println();

    }
}

