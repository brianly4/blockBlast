package block.blast;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class GameBoard {

    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLS = 10;
    private static final int TILE_SIZE = 40;

    private final GridPane gameBoard;
    private final boolean[][] occupiedTiles;
    private int score;
    private Text scoreLabel; // score label

    public GameBoard(Text scoreLabel) {
        this.gameBoard = createGameBoard();
        this.occupiedTiles = new boolean[BOARD_ROWS][BOARD_COLS];
        this.score = 0; // Initialize score
        this.scoreLabel = scoreLabel; // reference
    }

    private GridPane createGameBoard() {
        GridPane grid = new GridPane();

        // Set fixed size for the GridPane
        grid.setPrefSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);
        grid.setMaxSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);
        grid.setMinSize(BOARD_COLS * TILE_SIZE, BOARD_ROWS * TILE_SIZE);

        // Apply column and row constraints
        for (int i = 0; i < BOARD_COLS; i++) { // Avoid lambda issues with loop variables
            ColumnConstraints colConst = new ColumnConstraints(TILE_SIZE);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < BOARD_ROWS; i++) { // Avoid lambda issues with loop variables
            RowConstraints rowConst = new RowConstraints(TILE_SIZE);
            grid.getRowConstraints().add(rowConst);
        }

        // Add tiles to the grid
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLS; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                grid.add(tile, col, row); // Add tiles without referencing non-final variables in lambdas
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

    public void setTileUnoccupied(int row, int col) {
        occupiedTiles[row][col] = false;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int increment) {
        score += increment;
        updateScoreLabel();
    }

    public void updateScoreLabel() {
        scoreLabel.setText(String.valueOf(score));
    }

    public void checkAndClearFilledLines() {
        for (int row = 0; row < BOARD_ROWS; row++) {
            if (isRowFilled(row)) {
                clearRow(row);
                increaseScore(1); // Increment score by 1 for a filled row
                System.out.println("Row " + row + " cleared! Score: " + score);
            }
        }

        for (int col = 0; col < BOARD_COLS; col++) {
            if (isColumnFilled(col)) {
                clearColumn(col);
                increaseScore(1); // Increment score by 1 for a filled column
                System.out.println("Column " + col + " cleared! Score: " + score);
            }
        }
    }

    private boolean isRowFilled(int row) {
        for (int col = 0; col < BOARD_COLS; col++) {
            if (!occupiedTiles[row][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnFilled(int col) {
        for (int row = 0; row < BOARD_ROWS; row++) {
            if (!occupiedTiles[row][col]) {
                return false;
            }
        }
        return true;
    }

    private void clearRow(int row) {
        for (int col = 0; col < BOARD_COLS; col++) {
            final int currentCol = col; // Capture the column value
            // Remove only blocks (non-background tiles) at the given row and column
            gameBoard.getChildren().removeIf(node -> node instanceof Rectangle && // Ensure it's a block (Rectangle)
                    ((Shape) node).getFill() != Color.LIGHTGRAY && // Exclude the background grid tiles
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == currentCol);
            // Mark the tile as unoccupied
            occupiedTiles[row][col] = false;
        }
    }

    private void clearColumn(int col) {
        for (int row = 0; row < BOARD_ROWS; row++) {
            final int currentRow = row; // Capture the row value
            // Remove only blocks (non-background tiles) at the given row and column
            gameBoard.getChildren().removeIf(node -> node instanceof Rectangle && // Ensure it's a block (Rectangle)
                    ((Shape) node).getFill() != Color.LIGHTGRAY && // Exclude the background grid tiles
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == currentRow
                    && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col);
            // Mark the tile as unoccupied
            occupiedTiles[row][col] = false;
        }
    }
}