package server;
import common.WAMException;

/**
 * This class extends the threads and checks constantly for whether or not there was a whack in the system
 * @author: Sanchit Monga
 * @author: Helen Healy
 */

public class Whacked extends Thread {

    private WAMPlayer player;
    private WAM game;// instance of the game
    private static final int WHACK_SCORE=2;
    private static final int MISS_SCORE=1;
    private int time;
    private int score;

    public Whacked(WAM game,WAMPlayer player){
        this.game=game;
        this.player=player;
        this.time=game.getTime();
        this.score=0;
    }
    /**
     * Checking all the players constantly for the whack message
     * @throws WAMException
     */
    public void moleWhacked() throws WAMException {
        if(player.hasWhacked()) {
            int whack = player.whack();
            if (this.game.isMoleUp(whack)) {
                this.game.moveMoleDown(whack);
                score+=WHACK_SCORE;
            } else {
                score-=MISS_SCORE;
            }
        }
        game.scores1();
    }
    /**
     * @return: The updated score of the current whack player
     */
    public int getScore(){
        return this.score;
    }

    @Override
    public void run(){
        try {
            Long end=System.currentTimeMillis()+this.time;
            while(System.currentTimeMillis()<end) {// running this method till the end of the game
                moleWhacked();// constantly checking whether that player has whacked a mole or not
            }
            game.Scores();
        } catch (WAMException e) {
            e.printStackTrace();
        }
    }
}

