package block.blast;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene startScreen, gameScreen, endScreen;
    private GameBoard gameBoard; // Add GameBoard as a class field
    
    @Override
    public void start(Stage stage) throws Exception {
        // Load the image
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

        // Score values and buttons
        Text scoreValue = new Text("0");
        scoreValue.setFill(Color.WHITE);
        scoreValue.setFont(new Font(50));
        scoreValue.setX(300 - scoreValue.getBoundsInLocal().getWidth() / 2);
        scoreValue.setY(100);

        Button endGame = new Button("END GAME");
        endGame.setLayoutX(500); // Moved to the right side
        endGame.setLayoutY(20);  // Moved to the top
        endGame.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;"); // Added some styling
        gameLayout.getChildren().add(endGame);
        endGame.setOnAction(e -> {
            showEndScreen(stage);
        });

        // Initialize GameBoard
        gameBoard = new GameBoard(scoreValue);

        // Layouts
        gameLayout.getChildren().add(scoreValue);
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
        
        // Spawn initial blocks
        spawnInitialBlocks(gameLayout, gameBoard);
        gameScreen = new Scene(gameLayout, 600, 700);

        // Center the blockblasthome image horizontally
        pictureViewer.setTranslateX(190);
        // Center the play button horizontally
        menuClick.setTranslateX(180);

        // Create end screen
        createEndScreen();

        // Set up the stage
        stage.setScene(startScreen);
        stage.show();
        stage.setResizable(false);
    }

    private void createEndScreen() {
        Pane endLayout = new Pane();
        Text gameover = new Text("GAME OVER!");
        gameover.setFill(Color.WHITE);
        gameover.setFont(new Font(50));
        gameover.setX(300 - gameover.getBoundsInLocal().getWidth() / 2);
        gameover.setY(200);

        Text finalScore = new Text("Final Score: ");
        finalScore.setFill(Color.WHITE);
        finalScore.setFont(new Font(40));
        finalScore.setX(180);
        finalScore.setY(300);

        Text scoreValue = new Text();
        scoreValue.setId("endScore");
        scoreValue.setFill(Color.WHITE);
        scoreValue.setFont(new Font(40));
        scoreValue.setX(380);
        scoreValue.setY(300);

        endLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #1748b7, #147cd3);");
        endLayout.getChildren().addAll(gameover, finalScore, scoreValue);
        endScreen = new Scene(endLayout, 600, 700);
    }

    private void showEndScreen(Stage stage) {
        Text endScore = (Text) endScreen.lookup("#endScore");
        if (endScore != null) {
            endScore.setText(String.valueOf(gameBoard.getScore()));
        }
        stage.setScene(endScreen);
    }

    private void spawnInitialBlocks(Pane gameLayout, GameBoard gameBoard) {
        for (int i = 0; i < 3; i++) {
            Block initialBlock = Block.createRandomBlock();
            Pane blockPane = initialBlock.getBlockPane();

            double startX = 50 + (i * 200);
            double startY = 570;

            blockPane.setLayoutX(startX);
            blockPane.setLayoutY(startY);

            DragUtils.makeDraggable(blockPane, gameBoard, gameLayout);
            gameLayout.getChildren().add(blockPane);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}