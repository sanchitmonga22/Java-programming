package rit.stu.act2;
/**
 * Hostage object is creater in this class which implements the Player interface
 * author: Sanchit Monga
 */
public class Hostage extends Object implements Player{
    private int id;
    /**
     * Constructor used to initialize the current state of the constructor
     * @param id  ID of the hostage
     */
    public Hostage(int id){
        this.id=id;
    }
    /**
     *
     * @param player : String that represents and is used to declare the victory
     */
    public void victory(Player player){
        System.out.println(this.toString()+"+ yells, 'Victory over "+player+"+!'");
    }
    /**
     *
     * @param player: String that represents and is used to declare the defeat
     */
    public void defeat(Player player){
        System.out.println(this.toString()+" cries, 'Defeated by "+player+"!'");
    }
    /**
     *
     * @return: String representation of a hostage
     */
    @Override
    public String toString()
    {
        return "Hostage #"+id;
    }
}
