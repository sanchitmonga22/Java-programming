package server;
/**
 * An independent mole class that moves up and down
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class Mole extends Thread {
    /** the mole's number */
    private int number;
    /** time to play game */
    private int time;
    /** minimum time down */
    private final static int MIN_DOWN = 2000;
    /** min time up */
    private final static int MIN_UP = 3000;
    /** max time down */
    private final static int MAX_DOWN = 10000;
    /** max time up */
    private final static int MAX_UP = 5000;
    /** the WAM game */
    private WAM game;


    public Mole(WAM game, int number, int time) {
        this.number = number;
        this.time = time;
        this.game = game;
    }
    /**
     * Move the mole up in the game board
     */
    public void moveUp(){
        game.moveMoleUp(number);
    }

    /**
     * Move the mole down in the game board
     */
    public void moveDown(){
        game.moveMoleDown(number);
    }

    /**
     * Changes the mole status for the particular mole in the game board
     */
    @Override
    public void run() {
        Long t = System.currentTimeMillis();//present time
        Long end = t + time;
        while(System.currentTimeMillis()<end) {
            try {
                int downTime = MIN_DOWN + (int) (Math.random() * MAX_DOWN);
                int upTime = MIN_UP + (int) (Math.random() * MAX_UP);
                Thread.sleep(downTime);
                moveUp();
                Thread.sleep(upTime);
                moveDown();
            } catch (InterruptedException e) { }
        }
    }
}
