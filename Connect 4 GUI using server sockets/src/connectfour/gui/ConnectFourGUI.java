package connectfour.gui;

import connectfour.ConnectFourException;
import connectfour.client.ConnectFourBoard;
import connectfour.client.ConnectFourNetworkClient;
import connectfour.client.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * A JavaFX GUI for the networked Connect Four game.
 *
 * @author James Heloitis @ RIT CS
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    private ConnectFourBoard board;
    private ConnectFourNetworkClient serverConn;
    private Label move;
    private Label status1;
    private ArrayList<Button> buttons;
    private GridPane gridPane;
    int count;
    int prev;

    @Override
    public void init() {
        try {
            // get the command line args
            List<String> args = getParameters().getRaw();
            // get host info and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));
            this.board = new ConnectFourBoard();
            this.board.addObserver(this);
            this.serverConn = new ConnectFourNetworkClient(host, port, this.board);
        } catch (NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } catch (ConnectFourException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start(Stage stage) throws Exception {
        // Creating the buttons
        buttons = new ArrayList<>();
        for (int i = 0; i < this.board.COLS; i++) {
            Button button1 = new Button(Integer.toString(i));
            button1.setPrefWidth(64);
            button1.setOnAction(e -> {
                count = Integer.parseInt(button1.getText());
                if (this.board.isMyTurn()) {
                    this.serverConn.sendMove(count);
                }
            });
            buttons.add(button1);
        }
        move = new Label();
        move.setText(this.board.getMovesLeft() + " moves left");
        status1 = new Label();
        HBox up = new HBox();
        up.getChildren().addAll(buttons);
        HBox down = new HBox();
        down.getChildren().addAll(move, status1);
        down.setSpacing(100);
        gridPane = new GridPane();
        Image image = new Image(getClass().getResourceAsStream("empty.png"));
        for (int j = 0; j < this.board.COLS; j++) {
            for (int i = 0; i < this.board.ROWS; i++) {
                gridPane.add(new ImageView(image), j, i);
            }
        }
        BorderPane pane = new BorderPane();
        pane.setTop(up);
        pane.setCenter(gridPane);
        pane.setBottom(down);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        this.serverConn.startListener();
    }

    /**
     * GUI is closing, so close the network connection. Server will get the message.
     */
    @Override
    public void stop() {
        this.serverConn.close();
        System.exit(0);
    }

    /**
     * Do your GUI updates here.
     */
    private void refresh() {
        ConnectFourBoard.Status status = this.board.getStatus();// checking the status
        switch (status) {
            case ERROR:
                status1.setText("Error");
                break;
            case I_WON:
                status1.setText("You won. Yay!");
                break;
            case I_LOST:
                status1.setText("You lost. Boo!");
                break;
            case TIE:
                status1.setText("Tie game. Meh.");
                break;
        }
        int j = 0;
        if (board.isValidMove(count)) {// if the move is valid
            move.setText(this.board.getMovesLeft() + " moves left");// updating the move left
            this.buttons.get(prev).setText(Integer.toString(prev));
            for (j = 0; j < this.board.COLS; j++) {
                for (int i = 0; i < this.board.ROWS; i++) {
                    ConnectFourBoard.Move move = this.board.getContents(i, j);
                    if (move == ConnectFourBoard.Move.PLAYER_TWO) {
                        gridPane.add(new ImageView(new Image(getClass().getResourceAsStream("p2red.png"))), j, i);
                    } else if (move == ConnectFourBoard.Move.PLAYER_ONE) {
                        gridPane.add(new ImageView(new Image(getClass().getResourceAsStream("p1black.png"))), j, i);
                    }
                }
            }
        } else {
            this.buttons.get(count).setText("INVALID");
            prev=count;
        }
    }
    /**
     * Called by the model, client.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param connectFourBoard
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {
        if ( Platform.isFxApplicationThread() ) {
            this.refresh();
        }
        else {
            Platform.runLater( () -> this.refresh());
        }
    }
    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConnectFourGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
