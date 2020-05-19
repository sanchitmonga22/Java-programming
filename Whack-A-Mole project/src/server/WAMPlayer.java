package server;

import common.Duplexer;
import common.WAMException;
import common.WAMProtocol;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
/**
 * WAMPlayer
 * @author: Helen Healy
 * @author Sanchit Monga
 */
public class WAMPlayer implements WAMProtocol, Closeable {

    /** Player's number */
    private int playerNumber;
    /** The number of rows*/
    private int rows;
    /** The number of columns */
    private int cols;
    /** The amount of time left */
    private int time;

    private Duplexer server;

    /**
     * WAMPlayer constructor
     * @param socket socket to connect to client with
     */
    public WAMPlayer(Socket socket){
        try {
            server=new Duplexer(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * send server connect confirmation
     * @param rows rows on board
     * @param columns columns on board
     * @param players players playing
     * @param playerNum number of this player
     */
    public void connect(int rows,int columns,int players, int playerNum,int time){
        playerNumber=playerNum;
        this.rows=rows;
        this.cols=columns;
        this.time=time;
        server.send(WELCOME+" "+rows+" "+columns+" "+players+" "+playerNum);
    }

    /**
     * access the number of rows in the game
     * @return: The number of rows in the game
     */
    public int getRows(){
        return rows;
    }

    /**
     * access the number of columns in the game
     * @return: The number of columns in the game
     */
    public int getCols() {
        return cols;
    }

    /**
     * access the time
     * @return: The time for which the game was played
     */
    public int getTime() {
        return time;
    }
    /**
     * @return Whether there was a message received from the client
     */
    public boolean hasWhacked(){
        return server.nextLine();
    }
    /**
     * receives the whack message from the client side of the game
     * @return this player's scores
     * @throws WAMException
     */
    public int whack() throws WAMException {
        String response="";
        response=server.read();// this message will be read only if the player whacked a mole
        if (response.startsWith(WHACK)) {
            String[] tokens = response.split(" ");
            if (tokens.length == 3) {
                if (Integer.parseInt(tokens[2]) == playerNumber) {// only if the WAMPlayer reading the message is the same as the player that whacked
                    return Integer.parseInt(tokens[1]);
                } else {
                    return Integer.parseInt(null);
                }
            } else {
                this.error(response);
                throw new WAMException("Invalid player response: " + response);
            }
        }else {
            this.error(response);
            throw new WAMException("Invalid player response: " + response);
        }
    }

    /**
     * Send updated player score after whacking
     * @param scores
     */
    public void score(int[] scores){
        String ans=SCORE;
        for(int i=0;i<scores.length;i++){
            ans=ans+" "+scores[i];
        }
        server.send(ans);
    }
    /**
     * sending to client to move mole up
     * @param moNumber number of mole
     */
    public void moleUp(int moNumber){
        server.send(MOLE_UP+" "+moNumber);
    }

    /**
     * sending to client to move mole down
     * @param moNumber mole number
     */
    public void moleDown(int moNumber){
        server.send(MOLE_DOWN+" "+moNumber);
    }

    /**
     * send the Game Won message to client
     */
    public void gameWon(){
        server.send(GAME_WON);
    }

    /**
     * send the Game Lost message to client
     */
    public void gameLost(){
        server.send(GAME_LOST);
    }

    /** send the Tie Game message to client */
    public void gameTied(){
        server.send(GAME_TIED);
    }

    /**
     * send error message
     * @param message error message to send
     */
    public void error(String message){
        server.send(ERROR+" "+message);
    }

    /**
     * close sockets
     * @throws IOException
     */
    @Override
    public void close(){
        try{
            server.close();
        }catch (IOException ioe){ioe.printStackTrace();} catch (Exception e) {
            e.printStackTrace();
        }
    }
}
