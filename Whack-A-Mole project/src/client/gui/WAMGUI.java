package client.gui;
import common.WAMProtocol;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * WAM client GUI
 * @author Helen Healy
 * @author Sanchit Monga
 */
public class WAMGUI extends Application implements Observer<WAMBoard>, WAMProtocol {
    private WAMNetworkClient client;
    private WAMBoard board;
    private BorderPane borderPane;

    /**
     * The init function initializes the board and client
     */
    @Override
    public void init() {
        try {
            List<String> args = getParameters().getRaw();
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));
            this.board=new WAMBoard();
            this.board.addObserver(this);
            this.client = new WAMNetworkClient(host, port,board);
        } catch(NumberFormatException | IOException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * helper function that creates a gridpane containing the mole buttons
     * @return a GridPane
     */
    public GridPane makeGridpane(){
        GridPane gridPane = new GridPane();
        int rows = board.getRows();
        int cols = board.getColumns();
        for(int col=0; col<cols; col++){
            for(int row=0; row<rows; row++){
                int moleNum = cols*row + col;
                boolean up = board.getContents(moleNum).equals(WAMBoard.MoleStatus.UP);
                String str = "";
                if(up){
                    str = "moleU.png";
                }
                else{
                    str = "moleD.png";
                }
                Image image = new Image(getClass().getResourceAsStream(str));
                Button button = new Button();
                button.setGraphic(new ImageView(image));
                button.setOnAction(actionEvent ->
                                board.whack(moleNum));
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }
    /**
     * helper function creates score box
     * @return: A hbox containing all the buttons
     * */
    public HBox makeScoreBox() {
        HBox infobox = new HBox();
        String score = "Your Score: " + board.getScore();
        String status = "";
        WAMBoard.Status temp = board.getStatus();
        switch (temp) {
            case GAME_WON:
                status = "You Win! :)";
                break;
            case GAME_LOST:
                status = "You Lost! :(";
                break;
            case GAME_TIED:
                status = "Tie Game. :/";
                break;
            case ERROR:
                status = temp.toString();
                break;
            default:
                status = "Welcome..";
        }
        infobox.getChildren().addAll(new javafx.scene.control.Label(score),
                new javafx.scene.control.Label("                                             "),
                new Label(status));
        return infobox;
    }

    /**
     * helper function creates the scene
     * @return a scene
     */
    public Scene makeScene(){
        BorderPane bpane = new BorderPane();
        bpane.setCenter(makeGridpane());
        BorderPane.setAlignment(bpane, Pos.CENTER);
        bpane.setBottom(makeScoreBox());
        this.borderPane = bpane;
        return new Scene(bpane,700,700);
    }

    /**
     * start function starts the program
     * @param stage the stage to use
     * @throws Exception
     */
    @Override
    public void start(Stage stage){
        stage.setTitle("Whack-A-Mole");
        stage.setScene(makeScene());
        stage.show();
        client.startListener();
    }

    /**
     * stops all processes and closes client
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        client.close();
    }

    /**
     * refreshes the screen
     */
    private void refresh(){
        borderPane.setCenter(makeGridpane());
        borderPane.setBottom(makeScoreBox());
    }

    /**
     * called when there are state changes in the board
     * @param board the board
     */
    @Override
    public void update(WAMBoard board) {
        if(Platform.isFxApplicationThread()){
            this.refresh();
        }
        else{
            Platform.runLater(() -> this.refresh());
        }
    }

    /**
     * requires host and port as command line arguments
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WAMGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
