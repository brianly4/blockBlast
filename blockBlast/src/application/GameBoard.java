package application;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class GameBoard {

    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLS = 10;
    private static final int TILE_SIZE = 40;

    private GridPane gameBoard;

    public GameBoard() {
        this.gameBoard = createGameBoard();
    }

    private GridPane createGameBoard() {
        GridPane grid = new GridPane();
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLS; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                grid.add(tile, col, row);
            }
        }
        return grid;
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }
}

