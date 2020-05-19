package rit.stu.act2;
import rit.cs.Stack;
import rit.stu.act1.StackNode;
/**
 * This is the chopper that will be used to rescue all the players that are left after the battle and are taken to the safe location
 * Chopper is in the form of the stack data structure
 * author: Sanchit Monga
 */
public class Chopper extends Object{
    private Stack<Player> chopper;
    public static final int MAX_OCCUPANCY=6;// maximum capacity of the chopper
    private int numPassengers;
    private int numRescued;
    /**
     * Constructor to initialize the state of the class
     */
    public Chopper(){
        this.chopper=new StackNode<Player>();
        this.numPassengers=0;
        this.numRescued=0;
    }
    /**
     *
     * @return Whether the chopper is empty or not
     */
    public boolean isEmpty(){
        return this.chopper.empty();
    }
    /**
     *
     * @return whether the chopper is full or not
     */
    public boolean isFull(){
        if(this.numPassengers==MAX_OCCUPANCY){
            return true;
        }
        return false;
    }
    /**
     *
     * @return : The number of rescued passengers
     */
    public int getNumRescued(){
        return this.numRescued;
    }
    /**
     * Used to rescue all the passengers in the battlefield left after the battle
     */
    public void rescuePassengers(){
        while(!isEmpty()){
            System.out.println("Chopper transported "+chopper.pop()+" to safety!");
        }

    }
    /**
     * Board the passenger into the chopper stack
     * @param player: Boards the passenger into the chopper
     */
    public void boardPassenger(Player player){
        this.numRescued+=1;
        if(isFull()){
            this.rescuePassengers();
        }
        this.chopper.push(player);
    }
}