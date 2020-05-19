package rit.stu.act2;
import rit.cs.Queue;
import rit.cs.Stack;
import rit.stu.act1.QueueNode;
import rit.stu.act1.StackNode;
/**
 * Creating the enemy base where all the guerillas and hostages are staying
 * Guerillas are represented in the form of queue structure
 * Hostages are represented in the form of stack structures
 * author: Sanchit Monga
 */
public class EnemyBase extends Object {
    private Stack<Hostage> hostages;
    private Queue<Guerilla> guerillas;
    private int numHostages;
    private int numGuerillas;
    /**
     *
     * @param numHostages  : intial number of the hostages before the battle
     * @param numGuerillas: initial number of guerillas before the battle
     */
    public EnemyBase(int numHostages, int numGuerillas){
        this.numGuerillas=numGuerillas;
        this.numHostages=numHostages;
        hostages=new StackNode<Hostage>();
        for (int i=0;i<numHostages;i++){
            Hostage h1= new Hostage(i+1);
            addHostage(h1);// adding hostage into the stack structure
        }
        guerillas= new QueueNode<Guerilla>();
        for (int i=0;i<numGuerillas;i++){
            Guerilla g1= new Guerilla(i+1);
            addGuerilla(g1);// adding guerillas into the queue structure
        }
    }
    /**
     *
     * @param guerilla: Adding the guerilla in the queue
     */
    private void addGuerilla(Guerilla guerilla){
        guerillas.enqueue(guerilla);
    }
    /**
     *
     * @param hostage: Adding the given parameter into the stack of the hostages
     */
    private void addHostage(Hostage hostage){
        hostages.push(hostage);
    }
    /**
     *
     * @return : Returns the guerilla from the front of the queue
     */
    private Guerilla getGuerilla(){
        return guerillas.dequeue();
    }
    /**
     *
     * @return: Returns the hostages from the top of the stack
     */
    private Hostage getHostage(){
        return hostages.pop();
    }
    /**
     *
     * @return: Returns the number of the hostages left
     */
    public int getNumHostages(){
        return this.numHostages;
    }
    /**
     *
     * @return Returns the number of guerillas left in the queue
     */
    public int getNumGuerillas(){
        return this.numGuerillas;
    }
    /**
     *
     * @param soldier: Soldier in the battlefield that fill rescue the hostage
     * @return: The hostage if it was rescued successfully
     */
    public Hostage rescueHostage(Soldier soldier){
        System.out.println(soldier+" enters enemy base...");
        Hostage h1=getHostage();
        if(getNumGuerillas()==0){
            this.numHostages-=1;// hostage was returned
            return h1;
        }
        else{
            Guerilla guerilla= getGuerilla();
            int dice=Battlefield.nextInt(1,100);
            System.out.println(soldier+" battles "+guerilla+" who rolls a "+dice);
            if(dice>Guerilla.CHANCE_TO_BEAT_SOLDIER){
                soldier.victory(guerilla);
                guerilla.defeat(soldier);
                this.numGuerillas-=1;// guerilla was killed
                this.numHostages-=1;// hostage was returned
                return h1;
            }
            else{
                soldier.defeat(guerilla);
                guerilla.victory(soldier);
                addGuerilla(guerilla);
                addHostage(h1);
                return null;
            }
        }
    }
}
