package rit.stu.act2;
/**
 * Creating the soldier that will be deplyed in the battlefield
 * author: Sanchit Monga
 */
public class Soldier extends Object implements Player{
    private int id;
    /**
     * Contructor to initialize the state of the Soldier
     * @param id: ID of each soldier
     */
    public Soldier(int id){
        this.id=id;
    }
    /**
     * @param player: Declaring the victory of soldier over the opponent
     */
    public void victory(Player player){
        System.out.println(this.toString()+" yells, 'Sieg über "+player+"!'");
    }
    /**
     * @param player: Declaring the defeat of soldier against the opponent
     */
    public void defeat(Player player){
        System.out.println(this.toString()+" cries, 'Besiegt von "+player+"!'");
    }
    /**
     *
     * @return String repersentation of the soldier
     */
    @Override
    public String toString(){
        return "Soldier #"+this.id;
    }
}
