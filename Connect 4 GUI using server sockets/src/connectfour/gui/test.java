package connectfour.gui;
import connectfour.client.ConnectFourBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class test extends Application {
    private ArrayList<Button> buttons;
    private ConnectFourBoard board;
    private Label move;
    private Label status1;
    private GridPane gridPane;
    @Override

    public void start(Stage stage) throws Exception {
        this.board = new ConnectFourBoard();
        buttons=new ArrayList<>();
        move=new Label(this.board.getMovesLeft()+" moves left");
        status1=new Label();
        HBox down=new HBox();
        down.getChildren().addAll(move,status1);
        gridPane=new GridPane();
        //gridPane.getChildren().add(up);
        Image image=new Image(getClass().getResourceAsStream("empty.png"));

        for(int i=0;i<this.board.COLS;i++){
            gridPane.add(new Button("Col"+(i+1)),i,0);
        }
        for(int j=0;j<this.board.COLS;j++) {
            for (int i = 1; i < this.board.ROWS; i++) {
                gridPane.add(new ImageView(image),j,i);
            }
        }
        BorderPane pane=new BorderPane();
        //pane.setTop(up);
        pane.setCenter(gridPane);
        pane.setBottom(down);
        Scene scene=new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
