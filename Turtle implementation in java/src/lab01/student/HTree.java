/**
 *Author: Sanchit Monga
 * lang: Java
 */
package lab01.student;
import static turtle.Turtle.*;
import java.util.*;

public class HTree {
    public static final int MAX_SEGMENT_LENGTH = 200;
    /**
     * This function initizalizez the position of the turtle
     * @param length : Length of the side
     * @param depth: Number of recursions that the program needs to run
     */
    public static void init(int length, int depth) {
        Turtle.setWorldCoordinates(-length * 2, -length * 2, length * 2, length * 2);
        Turtle.title("H-Tree, depth: " + depth);
    }
    /**
     *This function draws the figure using the recursion technique and turtle graphics
     * @param length : Length of the side
     * @param depth: Number of recursions that the program needs to run
     */
    public static void drawHTree(double length, int depth) {
        if (depth > 0) {
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            drawHTree(length / 2, depth - 1);
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);
            drawHTree(length / 2, depth - 1);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length);
            Turtle.right(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            drawHTree(length / 2, depth - 1);
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);
            drawHTree(length / 2, depth - 1);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            Turtle.forward(length / 2);
        }
    }
    /**
     * This function calls the init and drawHTree function and draws the required figure
     * @param args: Takes the depth as the input from the user
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java HTree #");
        }
        int depth=Integer.valueOf(args[0]);
        if(depth<0){
            System.out.println("The depth must be greater than or equal to 0");
        }
        init(MAX_SEGMENT_LENGTH,depth);
        drawHTree(MAX_SEGMENT_LENGTH,depth);
        System.out.print("Hit enter to close...");
        Scanner in =new Scanner(System.in);
        String b= in.nextLine();
        if(b.equals("")){
            System.exit(0);
        }
    }
}
