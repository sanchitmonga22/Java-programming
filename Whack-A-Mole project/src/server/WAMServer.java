package server;
import common.WAMProtocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * WAM Server
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAMServer implements WAMProtocol, Runnable {
    private ServerSocket server;
    private int rows;
    private int columns;
    private int players;// number of players playing the game
    private int time;

    public WAMServer(int port, int rows, int columns, int players, int time) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rows = rows;
        this.columns = columns;
        this.players = players;
        this.time = time*1000;
    }
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java WAMServer <port> <rows> <columns> <number of players> <time in seconds>");
        }
        int port = Integer.parseInt(args[0]);
        int rows1 = Integer.parseInt(args[1]);
        int column1 = Integer.parseInt(args[2]);
        int players1 = Integer.parseInt(args[3]);
        int time1 = Integer.parseInt(args[4]);
        WAMServer server = new WAMServer(port, rows1, column1, players1, time1);
        server.run();
    }
    /**
     * Creating the players and running the main game
     */
    @Override
    public void run() {
        WAMPlayer[] playersList=new WAMPlayer[players];
        for (int i = 0; i < players; i++) {
            try {
                Socket playerSocket = server.accept();
                WAMPlayer player = new WAMPlayer(playerSocket);
                player.connect(this.rows, this.columns, this.players, i,this.time);
                playersList[i]=player;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WAMGame game=new WAMGame(playersList);
        new Thread(game).run();
    }
}
