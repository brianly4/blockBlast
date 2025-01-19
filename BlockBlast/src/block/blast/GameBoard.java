package block.blast;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoard {

    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLS = 10;
    private static final int TILE_SIZE = 40;

    private final GridPane gameBoard;
    private final boolean[][] occupiedTiles;

    public GameBoard() {
        this.gameBoard = createGameBoard();
        this.occupiedTiles = new boolean[BOARD_ROWS][BOARD_COLS];
    }

    private GridPane createGameBoard() {
        GridPane grid = new GridPane();

        // Set fixed size for the GridPane
        grid.setPrefSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);
        grid.setMaxSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);
        grid.setMinSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);

        // Apply column and row constraints
        for (int i = 0; i < BOARD_COLS; i++) {
            ColumnConstraints colConst = new ColumnConstraints(TILE_SIZE);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < BOARD_ROWS; i++) {
            RowConstraints rowConst = new RowConstraints(TILE_SIZE);
            grid.getRowConstraints().add(rowConst);
        }

        // Add tiles to the grid
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLS; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                grid.add(tile, col, row);
            }
        }

        grid.setAlignment(Pos.CENTER); // Center the GridPane
        return grid;
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public boolean isTileOccupied(int row, int col) {
        return occupiedTiles[row][col];
    }

    public void setTileOccupied(int row, int col) {
        occupiedTiles[row][col] = true;
    }
}
