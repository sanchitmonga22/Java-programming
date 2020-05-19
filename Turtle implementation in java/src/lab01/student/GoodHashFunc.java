/**
 *Author: Sanchit Monga
 * lang: Java
 */
package lab01.student;
import java.util.*;
public class GoodHashFunc{
    /**
     * This function is used to calculate the hash value of the String.
     * @param s: This is a string whose hash value has to be calculated
     * @return: Returns the good hash value of the String entered by the user
     */
    public static int computeHash(String s){
        int [] key=new int[s.length()];
        int d= s.length()-1;
        for (int j=0; j<s.length();j++){
            int f=(int)Math.pow(31,d);
            key[j] = s.charAt(j)*f;
            d--;
        }
        int k=s.length()-1;
        int sum=0;
        while(k>=0){
            sum+=key[k];
            k--;
        }
        return sum;
    }

    /**
     * This function takes the input from the user and invoked the computeHash function to calculate the good hash value and prints it.
     * @param args: Takes the argument of the String from the user using the scanner class
     */
    public static void main(String [] args){
        Scanner in =new Scanner(System.in);
        System.out.print("Please enter a string: ");
        String inp=in.nextLine();
        System.out.println("The computed hash for the specified string is: "+computeHash(inp));
    }
}
