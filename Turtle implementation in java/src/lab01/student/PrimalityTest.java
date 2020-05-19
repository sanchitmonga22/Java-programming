/**
 *Author: Sanchit Monga
 * lang: Java
 */
package lab01.student;
import java.util.*;
public class PrimalityTest {
    /**
     * This function checks whether the integer is a prime number or not
     * @param n: This is an integer that is to be checked
     * @return : Returns a boolean value, True if the number is prime, else it returns false
     */
    public static boolean isPrime(int n){
        if(n==1){
            return false;
        }
        int count=0;
        for (int i=1; i<=n; i++){
            if(n%i==0){
                count++;
            }
        }
        if(count == 2){
            return true;
        }
        return false;
    }

    /**
     * This function takes the input from the user and invokes the idPrime function to check whether the number is prime or not
     * @param args: Takes the integer as the input from the user using scanner class
     */
    public static void main(String[] args){
        while(true){
            Scanner in =new Scanner(System.in);
            System.out.print("Enter a number (0 to quit): ");
            String b=in.next();
            int c=Integer.valueOf(b);
            if(c==0){
                System.out.println("Goodbye!");
                break;
            }
            else{
                if(isPrime(c)==true){
                    System.out.println(c+" is prime!");
                }
                else{
                    System.out.println(c+" is not prime.");
                }
            }
        }
        boolean b=isPrime(2);
        System.out.print(b);
    }
}
