package client.gui;

import java.util.LinkedList;
import java.util.List;
/**
 * This class represents the board of the game which has moles in it
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAMBoard {
    /**
     * For the status of the game
     */
    public enum Status {
        NOT_OVER, GAME_WON, GAME_LOST, GAME_TIED, ERROR;
    }
    /**
     *  Enum created for the status of the mole
     */
    public enum MoleStatus{
        UP,DOWN
    }
    private List<Observer<WAMBoard>> observers;// list of observers for this class
    /**
     * @param observer : Adding the observer observing the board
     */
    public void addObserver(Observer<WAMBoard> observer) {// adding the observer of the board
        this.observers.add(observer);
    }
    /**
     * Alerting the observer that a change was made
     */
    private void alertObservers() {// for alerting the observers
        for (Observer<WAMBoard> observer: this.observers ) {
            observer.update(this);
        }
    }
    private int moleWhacked;// The number of the mole which was whacked
    private boolean whacked;// whether it was whacked or not
    private MoleStatus[] board;//  array to position the mole in the board
    private int rows;// number of rows in the board
    private int columns;// number of columns in the board
    private Status status;// status of the game
    private int score;// score of the client
    /**
     *Initializing the state of the board
     **/
    public WAMBoard(){
        this.observers = new LinkedList<>();
        this.score=0;
        this.whacked=false;
        this.status=Status.NOT_OVER;
    }

    /**
     * To initialize the board mole array
     * @param rows number of rows in the board
     * @param columns number of columns in the board
     */
    public void initiliaze(int rows, int columns) {
        this.rows=rows;
        this.columns=columns;
        int temp = columns*rows;
        this.board=new MoleStatus[temp];
        for(int i = 0;i < temp; i++) {
            board[i] = MoleStatus.DOWN;
        }
        alertObservers();
    }

    /**
     * @return: number of rows in the board
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * @return number of columns in the board
     */
    public int getColumns(){
        return this.columns;
    }

    /**
     * @param s the score of the player
     */
    public void updateScore(int s){
        this.score=s;
        alertObservers();
    }

    /**
     * Moving the mole up
     * @param num: the number of the mole which went up
     */
    public void moleUp(int num){
        this.board[num]=MoleStatus.UP;
        alertObservers();
    }

    /**
     * Moving the mole dowm
     * @param num: the number of the mole which went down
     */
    public void moleDown(int num){
        this.board[num]=MoleStatus.DOWN;
        alertObservers();
    }
    /**
     * Called if the game was won
     */
    public void gameWon(){
        this.status=Status.GAME_WON;
        alertObservers();
    }
    /**
     * Called the player lost the game
     */
    public void gameLost(){
        this.status=Status.GAME_LOST;
        alertObservers();
    }
    /**
     * Called if the game was a tie
     */
    public void gameTied(){
        this.status=Status.GAME_TIED;
        alertObservers();
    }
    /**
     * Called if there was an error in the protocols
     */
    public void error(){
        this.status=Status.ERROR;
        alertObservers();
    }
    /**
     * To check whether the mole was whacked or not
     */
    public boolean inform(){
        return whacked;
    }
    /**
     * @return: The mole that was whacked
     */
    public int getMoleWhacked(){
        return moleWhacked;
    }
    /**
     * To reset the mole status
     */
    public void setMoleBack(){
        whacked=false;
    }
    /**
     * To close the board
     */
    public void close(){
        alertObservers();
    }
    // following functions will be used by the GUI
    /**
     * @return: The status of the game
     */
    public Status getStatus(){
        return this.status;
    }
    /**
     * @param num: the number of the mole
     * @return: the mole of that particular number
     */
    public MoleStatus getContents(int num){
        return this.board[num];
    }
    /**
     * @return The current score of the client
     */
    public int getScore(){
        return this.score;
    }
    /**
     * Called when the button is clicked by the WAMGUI
     * @param num: Alerts the client that a mole was whacked
     */
    public void whack(int num){
        moleWhacked=num;// number of the Mole whacked
        alertObservers();
        whacked=true;
    }
}

