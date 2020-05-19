/**
 *Author: Sanchit Monga
 * lang: Java
 */
package lab01.student;
import java.util.*;

public class SieveOfEratosthenes {
    /**
     * This function uses the Sieve of Eratosthesnes algorithm to compute a list of prime numbers
     * @param upperBound: This function takes an upperbound and computes whether the list of numbers is prime or not till the upperbound
     * @return : Returns an array of integers which specifies the number at a particular index is prime or not
     */
    public static int[] makeSieve(int upperBound) {
        int[] d = new int[upperBound];
        for (int i = 0; i < upperBound; i++) {
            d[i] = 0;
        }
        d[1] = 1;
        d[0]=1;
        int k = 2;
        while (k < upperBound) {
            int p = 2;
            if (d[k] == 0) {
                while (p * k < upperBound) {
                    d[p * k] = 1;
                    p++;
                }
            }
            k++;
        }
        return d;
    }
    /**
     * This function passes the value of upperbound to the makeSieve funciton and stores the returned value in the array and then outputs whether the number eneterd by the user in the range is prime or not
     * @param args: Takes the upperbound whihc is an int as the argument
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter an upper bound: ");
        String ub = in.next();
        int c = Integer.valueOf(ub);
        int[] b = new int[c];
        b = makeSieve(c+1);
        while (true) {
            System.out.print("Enter a number (0 to quit): ");
            String n1 = in.next();
            int n = Integer.valueOf(n1);
            if (n == 0) {
                System.out.println("Goodbye!");
                break;
            } else {
                if (b[n] == 0) {
                    System.out.println(n + " is prime!");
                } else {
                    System.out.println(n + " is not prime.");
                }
            }
        }
    }
}
