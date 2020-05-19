/**
 * This is the bunker where all the soldiers are staying
 * The bunker is similar to a queue structure
 * author: Sanchit Monga
 */
package rit.stu.act2;
import rit.stu.act1.QueueNode;

public class Bunker extends Object{
    private QueueNode<Soldier> bunker;
    private int numSoldiers;
    /**
     * onstructor to initialize the bunker
     * @param numSoldiers : Number of soldiers in the bunker initially
     */
    public Bunker(int numSoldiers){
        this.numSoldiers=numSoldiers;
        bunker=new QueueNode<Soldier>();
        for(int i=0;i<numSoldiers;i++){// initializing the bunker queue
            Soldier s1=new Soldier(i+1);
            bunker.enqueue(s1);
        }
    }
    /**
     *
     * @return Whether there are any soldiers lef tin there or not
     */
    public boolean hasSoldiers(){
        return !this.bunker.empty();
    }
    /**
     *
     * @return The number of soldiers in the queue
     */
    public int getNumSoldiers(){
        return this.numSoldiers;
    }
    /**
     *
     * @return Deploys the next soldier in the battlefield by pooping the soldier from the front of the queue
     */
    public Soldier deployNextSoldier(){
        this.numSoldiers-=1;
        return bunker.dequeue();
    }
    /**
     *
     * @param soldier: Soldier that has to be added back to the end of the queue
     */
    public void fortifySoldiers(Soldier soldier){
        this.numSoldiers+=1;
        bunker.enqueue(soldier);
    }
}
