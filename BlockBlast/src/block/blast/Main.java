package block.blast;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    Scene startScreen, gameScreen, endScreen;

    @Override
    public void start(Stage stage) throws Exception {

        // Load the images
        Image picture2 = new Image("file:images/playButton.JPG", 200, 0, true, true);
        Image picture = new Image("file:images/blockblasthome.png", 200, 0, true, true);

        ImageView pictureViewer = new ImageView(picture);
        ImageView pictureViewer2 = new ImageView(picture2);

        // Set the images and button to be centered horizontally
        pictureViewer.setTranslateY(100);

        // Create the button
        Button menuClick = new Button();
        menuClick.setOnAction(e -> stage.setScene(gameScreen));
        menuClick.setGraphic(pictureViewer2);
        menuClick.setTranslateY(420);

        // Use Pane for absolute positioning
        Pane startLayout = new Pane();
        startLayout.getChildren().addAll(pictureViewer, menuClick);

        // Set the background gradient
        startLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #1748b7, #147cd3);");

        // Create the scene
        startScreen = new Scene(startLayout, 600, 700);

        // Set up the game layout
        Pane gameLayout = new Pane();
        GameBoard gameBoard = new GameBoard();
        gameLayout.getChildren().add(gameBoard.getGameBoard());
        gameLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #1748b7, #147cd3);");

        // Center the game board in the game layout
        gameBoard.getGameBoard().layoutXProperty().bind(Bindings.createDoubleBinding(
            () -> (gameLayout.getWidth() - gameBoard.getGameBoard().getWidth()) / 2,
            gameLayout.widthProperty(),
            gameBoard.getGameBoard().widthProperty()
        ));
        gameBoard.getGameBoard().layoutYProperty().bind(Bindings.createDoubleBinding(
            () -> (gameLayout.getHeight() - gameBoard.getGameBoard().getHeight()) / 2,
            gameLayout.heightProperty(),
            gameBoard.getGameBoard().heightProperty()
        ));

        // Spawn initial block
        spawnInitialBlock(gameLayout, gameBoard);

        gameScreen = new Scene(gameLayout, 600, 700);

        // Center the blockblasthome image horizontally
        pictureViewer.setTranslateX(190);

        // Center the play button horizontally
        menuClick.setTranslateX(180);

        // Set up the stage
        stage.setScene(startScreen);
        stage.show();
        stage.setResizable(false); // Lock screen size
    }

    private void spawnInitialBlock(Pane gameLayout, GameBoard gameBoard) {
        Block initialBlock = Block.createRandomBlock();
        Pane blockPane = initialBlock.getBlockPane();
        double startX = 50; // Starting X position for blocks
        double startY = gameBoard.getGameBoard().getBoundsInParent().getMaxY() + 20; // Starting Y position for blocks below the grid
        blockPane.setLayoutX(startX);
        blockPane.setLayoutY(startY);
        DragUtils.makeDraggable(blockPane, gameBoard, gameLayout); // Make block draggable
        gameLayout.getChildren().add(blockPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}