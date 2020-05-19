package rit.stu.act2;
/**
 * Implementing the predator class that will fight the soldier and the hostage
 * author: Sanchit Monga
 */
public class Predator extends Object implements Player{
    public static final int CHANCE_TO_BEAT_HOSTAGE=75;
    public static final int CHANCE_TO_BEAT_SOLDIER=50;
    /**
     * Constructor to initialize the intial state of the class
     */
    public Predator(){
    }
    /**
     *
     * @param player Declaring the victory of predator over a player
     */
    public void victory(Player player){
        System.out.println("The predator yells out in triumphant victory over "+player+"!");
    }
    /**
     *
     * @param player declaring the defeat of predator against a player
     */
    public void defeat (Player player){
        System.out.println("The predator cries out in glorious defeat to "+player+"!");
    }

    /**
     *
     * @return: String representation of the predator
     */
    @Override
    public String toString(){
        return "Predator";
    }
}
