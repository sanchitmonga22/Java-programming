package client.gui;
import common.*;

import java.io.IOException;
import java.net.Socket;

/**
 * This class handles all the protocols received from the server side and updates the game and the GUI
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAMNetworkClient implements WAMProtocol{

    private Socket clientSocket;
    private WAMBoard board;
    private Duplexer client;
    private boolean serverStatus;
    private int rows;
    private int columns;
    private int players;
    private int playerNumber;
    /**
     * Constructor to initialize the state of the class
     * @param host: Hostame
     * @param port: Port number
     * @param board: The board that has to be updated
     * @throws IOException
     */
    public WAMNetworkClient(String host, int port,WAMBoard board) throws IOException{
        this.clientSocket = new Socket(host, port);
        client=new Duplexer(clientSocket);
        serverStatus=true;
        this.board=board;
    }
    /**
     * The main loop that runs the client side of the game
     */
    public void run() throws WAMException {
        while(serverStatus){
            if(!client.nextLine()){
                throw new WAMException("Server not responding");
            }
            String message=client.read();// reading the message from the server
            String[] mess=message.split(" ");
            switch (mess[0]){
                case WELCOME:
                    System.out.println("connected to the server");
                    this.rows= Integer.parseInt(mess[1]);
                    this.columns=Integer.parseInt(mess[2]);
                    this.players=Integer.parseInt(mess[3]);
                    this.playerNumber=Integer.parseInt(mess[4]);
                    this.board.initiliaze(this.rows,this.columns);// initialing the board and passing the number of rows and columns
                    break;
                case SCORE:
                    int score=Integer.parseInt(mess[this.playerNumber+1]);// returning the score of the current client
                    this.board.updateScore(score);
                    break;
                case MOLE_UP:
                    int num=Integer.parseInt(mess[1]);
                    this.board.moleUp(num);
                    break;
                case MOLE_DOWN:
                    int num1=Integer.parseInt(mess[1]);
                    this.board.moleDown(num1);
                    break;
                case GAME_WON:
                    this.board.gameWon();
                    serverStatus=false;
                    break;
                case GAME_LOST:
                    this.board.gameLost();
                    serverStatus=false;
                    break;
                case GAME_TIED:
                    this.board.gameTied();
                    serverStatus=false;
                    break;
                case ERROR:
                    this.board.error();
                    serverStatus=false;
                    break;
            }
            if(this.board.inform()){// if the mole was whacked, sending message to the server
                client.send(WHACK+" "+this.board.getMoleWhacked()+" "+this.playerNumber);
                this.board.setMoleBack();// once the move has been played it is set back to false
            }
        }
    }
    /**
     * For closing the client side of the game
     * @throws Exception
     */
    public void close() throws Exception {
        client.close();
        this.board.close();
    }
    /**
     * To create and start a thread of the run method when the GUI is initialized and running
     */
    public void startListener() {
        new Thread(() -> {
            try {
                this.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}


