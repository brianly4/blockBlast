package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int TILE_SIZE = 40; // Size of each square tile
    private static final int BOARD_ROWS = 8; // Rows in the board
    private static final int BOARD_COLS = 8; // Columns in the board

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Create the game board
        GridPane gameBoard = createGameBoard();
        gameBoard.setLayoutX(125); // Position of the game board
        gameBoard.setLayoutY(165);

        // Create draggable blocks
        List<Block> blocks = createBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            block.getBlockPane().setLayoutX(30 + i * 150); // Space out blocks
            block.getBlockPane().setLayoutY(520);
            makeDraggable(block.getBlockPane());
            root.getChildren().add(block.getBlockPane());
        }

        // Add everything to the root
        root.getChildren().add(gameBoard);

        Scene scene = new Scene(root, 600, 800);
        primaryStage.setTitle("Block Blast!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a game board grid
    private GridPane createGameBoard() {
        GridPane grid = new GridPane();
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLS; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK); // Add grid lines
                grid.add(tile, col, row);
            }
        }
        return grid;
    }

    // Create blocks
    private List<Block> createBlocks() {
        List<Block> blocks = new ArrayList<>();

        // Example block shapes
        int[][] squareShape = {
            {1, 1},
            {1, 1}
        };

        int[][] lShape = {
            {1, 0},
            {1, 0},
            {1, 1}
        };

        int[][] tShape = {
            {1, 1, 1},
            {0, 1, 0}
        };
        
        int[][] upsideDownTShape = {
        		{0, 1, 0},
                {1, 1, 1}
            };
        
        int[][] zShape = {
        		{1, 1, 0},
                {0, 1, 1}
            };

        blocks.add(new Block(squareShape, TILE_SIZE, Color.BLUE));
        blocks.add(new Block(lShape, TILE_SIZE, Color.RED));
        blocks.add(new Block(tShape, TILE_SIZE, Color.GREEN));
        blocks.add(new Block(upsideDownTShape, TILE_SIZE, Color.GREEN));
        blocks.add(new Block(zShape, TILE_SIZE, Color.GREEN));
        

        return blocks;
    }

    // Make blocks draggable
    private void makeDraggable(Pane blockPane) {
        final double[] offset = new double[2];
        blockPane.setOnMousePressed((MouseEvent event) -> {
            offset[0] = event.getSceneX() - blockPane.getLayoutX();
            offset[1] = event.getSceneY() - blockPane.getLayoutY();
        });

        blockPane.setOnMouseDragged((MouseEvent event) -> {
            blockPane.setLayoutX(event.getSceneX() - offset[0]);
            blockPane.setLayoutY(event.getSceneY() - offset[1]);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Block class to represent each shape
    static class Block {
        private final Pane blockPane; // Pane to hold the block's tiles
        private final int[][] shape; // 2D array representing the block's structure
        private final int tileSize;  // Size of each tile

        public Block(int[][] shape, int tileSize, Color color) {
            this.shape = shape;
            this.tileSize = tileSize;
            this.blockPane = new Pane();

            drawBlock(color);
        }

        private void drawBlock(Color color) {
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] == 1) { // Only draw if the cell is filled
                        Rectangle rect = new Rectangle(tileSize, tileSize);
                        rect.setFill(color);
                        rect.setStroke(Color.BLACK); // Optional: Add a border
                        rect.setX(col * tileSize);   // Set horizontal position
                        rect.setY(row * tileSize);   // Set vertical position

                        blockPane.getChildren().add(rect); // Add to the block pane
                    }
                }
            }
        }

        public Pane getBlockPane() {
            return blockPane;
        }
    }
}