package rit.stu.act2;
/**
 * Creating the Guerilla that will fight the soldier and the hostages
 * author: Sanchit Monga
 */
public class Guerilla extends Object implements Player {
    public static final int CHANCE_TO_BEAT_SOLDIER=20;
    private int id;
    /**
     *
     * @param id ID that represents the Guerilla
     */
    public Guerilla(int id){
        this.id=id;
    }
    /**
     *
     * @param player: Declaring the victory of the Guerilla over the opponent
     */
    public void victory(Player player){
        System.out.println(this.toString()+" yells, 'Victoria sobre "+player+"!'");
    }
    /**
     *
     * @param player: Declaring the defaet of the Guerilla against the opponent
     */
    public void defeat(Player player){
        System.out.println(this.toString()+" cries, 'Derrotado por "+player+"!'");
    }
    /**
     * @return: String representation of the Guerilla
     */
    @Override
    public String toString(){
        return "Guerilla #"+id;
    }

}
