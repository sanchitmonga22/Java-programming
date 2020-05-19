package server;
import client.gui.WAMBoard;
import java.util.ArrayList;
/**
 * TO keep track of status of all the moles in the board
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAM{
    /** number of rows */
    private int rows;
    /** number of columns */
    private int cols;
    /** board of Moles */
    private WAMBoard.MoleStatus[] board;
    /** players list */
    private ArrayList<WAMPlayer> players;
    /** Game play time */
    private int time;
    /** list of scores */
    private int[] scores;
    /** list of whacks */
    private ArrayList<Whacked> whacks;

    /**
     * Constructor
     * @param rows in the board
     * @param cols in the board
     * @param players playing
     * @param time to play
     */
    public WAM(int rows, int cols, ArrayList<WAMPlayer> players,int time){
        this.time=time;
        this.players=new ArrayList<>();
        this.players.addAll(players);
        this.rows=rows;
        this.cols=cols;
        board=new WAMBoard.MoleStatus[rows*cols];
        for(int i=0;i<board.length;i++){
            board[i]= WAMBoard.MoleStatus.DOWN;
        }
        scores=new int[players.size()];
        whacks=new ArrayList<>();
    }
    /**
     * @return: The total duration for which the game is being played
     */
    public int getTime(){
        return this.time;
    }
    /**
     * @param num: The number of the mole that is being checked
     * @return: Whether the mole was up or not
     */
    public boolean isMoleUp(int num){
        return board[num]== WAMBoard.MoleStatus.UP;
    }

    /**
     * moves mole up
     * @param num the number of the mole to move down
     */
    public synchronized void moveMoleUp(int num){
        board[num]=WAMBoard.MoleStatus.UP;
        sendMoleStatus(true,num);
    }

    /**
     * moves mole down
     * @param num the number of the mole to move down
     */
    public synchronized void moveMoleDown(int num){
        board[num]= WAMBoard.MoleStatus.DOWN;
        sendMoleStatus(false,num);
    }

    /**
     * Sending all the clients the message whether the player was up or not
     * @param up: if the mole was up or not
     * @param num: the number of the mole
     */
    private void sendMoleStatus(boolean up,int num){
        for(WAMPlayer player:players){
            if(up) {
                player.moleUp(num);
            }
            else{
                player.moleDown(num);
            }
        }
    }

    /**
     * Creating the threads of the whacked class and starting them
     * Whack class constantly checks for the whack message received from the clients
     */
    public void moleWhacked(){
        for(int i=0;i<this.players.size();i++) {
            Whacked w=new Whacked(this,this.players.get(i));
            new Thread(w).start();
            whacks.add(w);
        }
    }

    /**
     * This function is used to constantly update the scores after one of the players have whacked
     */
    public void scores1(){
        for(int i=0;i<players.size();i++){
            scores[i]=whacks.get(i).getScore();
        }
        for(WAMPlayer player: players){
            player.score(scores);
        }
    }

    /**
     * Function called at the end of the game to send the final status and scores
     */
    public void Scores() {
        if (players.size() == 1) {
            this.players.get(0).gameWon();
        } else {
            int maxIndex = 0;
            int max = -Integer.MAX_VALUE;
            for (int i = 0; i < players.size(); i++) {
                if (scores[i] > max) {
                    max = scores[i];
                    maxIndex = i;
                    break;
                }
            }
            ArrayList<Integer> tie = new ArrayList<>();// storing the index of on or more players if there is a tie between them
            for(int i=0;i<players.size();i++){
                if(scores[maxIndex]==scores[i]){
                    tie.add(i);
                }
            }
            // checking if there is just one element that means there is one winner
            if(tie.size()==1) {
                this.players.get(maxIndex).gameWon();
            }else{// or when there are more than one elements that means there is a tie
                for(int i=0;i<tie.size();i++){
                    this.players.get(tie.get(i)).gameTied();
                }
            }
            //All other players other than the ones in tie will lose
            for(int i=0;i<players.size();i++){
                if(!tie.contains(i)){
                    this.players.get(i).gameLost();
                }
            }
        }
    }
}
