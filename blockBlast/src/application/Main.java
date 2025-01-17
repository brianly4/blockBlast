package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        GameBoard gameBoard = new GameBoard();
        root.getChildren().add(gameBoard.getGameBoard());

        List<Block> blocks = Block.createBlocks();
        for (Block block : blocks) {
            root.getChildren().add(block.getBlockPane());
            DragUtils.makeDraggable(block.getBlockPane()); // Make blocks draggable
        }

        primaryStage.setTitle("Block Blast Game");
        Scene scene = new Scene(root, 400, 510);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
