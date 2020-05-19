package server;
import java.util.ArrayList;

/**
 * WAM Game
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAMGame implements Runnable{
    /** is the game running? */
    /** rows on the board */
    private ArrayList<WAMPlayer> players;
    /** the array of scores */
    private WAM game;
    private int rows;
    private int cols;
    private int gameTime;
    /**
     * constructor for the WAM Game
     *@param players all the players playing the game
     */
    public WAMGame(WAMPlayer... players){
        this.players=new ArrayList<>();
        for(int i=0;i<players.length;i++){// initializing the arrayList and the scores of each player
            this.players.add(players[i]);
        }
        this.rows=this.players.get(0).getRows();
        this.cols=this.players.get(0).getCols();
        this.gameTime=this.players.get(0).getTime();
        game=new WAM(rows,cols,this.players,this.gameTime);// passing the number of rows, cols and time into the WAM
    }
    /**
     * Main loop that runs the whole game
     */
    @Override
    public void run() {// new moles will be created here
        for(int i=0;i<rows*cols;i++){// starting the threads for the moles
            new Thread(new Mole(game,i,gameTime)).start();
        }
        this.game.moleWhacked();// calling this function to make threads for all the whacks
    }
}
