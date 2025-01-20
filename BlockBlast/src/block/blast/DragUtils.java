package block.blast;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class DragUtils {

    private static double startX;
    private static double startY;

    private static int blocksPlaced = 0;

    public static void makeDraggable(Pane blockPane, GameBoard gameBoard, Pane gameLayout) {
        final double[] offset = new double[2];

        blockPane.setOnMousePressed((MouseEvent event) -> {
            offset[0] = event.getSceneX() - blockPane.getLayoutX();
            offset[1] = event.getSceneY() - blockPane.getLayoutY();
            startX = blockPane.getLayoutX();
            startY = blockPane.getLayoutY();
        });

        blockPane.setOnMouseDragged((MouseEvent event) -> {
            blockPane.setLayoutX(event.getSceneX() - offset[0]);
            blockPane.setLayoutY(event.getSceneY() - offset[1]);
        });

        blockPane.setOnMouseReleased((MouseEvent event) -> {
            double x = blockPane.getLayoutX();
            double y = blockPane.getLayoutY();

            // Debugging output
            System.out.println("Mouse released at: " + x + ", " + y);

            if (isWithinBounds(x, y, gameBoard.getGameBoard())) {
                if (canPlaceBlock(blockPane, gameBoard)) {
                    attachBlockToBoard(blockPane, gameBoard);
                    gameLayout.getChildren().remove(blockPane);
                    blocksPlaced++;

                    if (blocksPlaced == 3) {
                        blocksPlaced = 0;
                        spawnNewBlocks(gameLayout, gameBoard);
                    }
                } else {
                    System.out.println("Block placement failed: At least one cell is occupied.");
                    blockPane.setLayoutX(startX);
                    blockPane.setLayoutY(startY);
                }
            } else {
                System.out.println("Block placement failed: Out of bounds.");
                blockPane.setLayoutX(startX);
                blockPane.setLayoutY(startY);
            }

        });
    }

    private static boolean isWithinBounds(double x, double y, GridPane grid) {
        Bounds bounds = grid.getBoundsInParent();
        return bounds.contains(x, y);
    }

    private static boolean canPlaceBlock(Pane blockPane, GameBoard gameBoard) {
        double tileSize = gameBoard.getTileSize();
        double startX = blockPane.getLayoutX();
        double startY = blockPane.getLayoutY();

        int startCol = (int) Math.round((startX - gameBoard.getGameBoard().getLayoutX()) / tileSize);
        int startRow = (int) Math.round((startY - gameBoard.getGameBoard().getLayoutY()) / tileSize);

        for (int i = 0; i < blockPane.getChildren().size(); i++) {
            Rectangle tile = (Rectangle) blockPane.getChildren().get(i);
            int gridCol = startCol + (int) Math.round(tile.getX() / tileSize);
            int gridRow = startRow + (int) Math.round(tile.getY() / tileSize);

            // Check for bounds and occupancy
            if (gridRow < 0 || gridRow >= 10 || gridCol < 0 || gridCol >= 10
                    || gameBoard.isTileOccupied(gridRow, gridCol)) {
                return false;
            }
        }

        return true;
    }

    private static void attachBlockToBoard(Pane blockPane, GameBoard gameBoard) {
        double tileSize = gameBoard.getTileSize();
        double startX = blockPane.getLayoutX();
        double startY = blockPane.getLayoutY();

        int startCol = (int) Math.round((startX - gameBoard.getGameBoard().getLayoutX()) / tileSize);
        int startRow = (int) Math.round((startY - gameBoard.getGameBoard().getLayoutY()) / tileSize);

        // Add each tile of the block to the grid
        for (int i = 0; i < blockPane.getChildren().size(); i++) {
            Rectangle tile = (Rectangle) blockPane.getChildren().get(i);

            int gridCol = startCol + (int) Math.round(tile.getX() / tileSize);
            int gridRow = startRow + (int) Math.round(tile.getY() / tileSize);

            // Create a new rectangle to represent the tile in the grid
            Rectangle gridTile = new Rectangle(tileSize, tileSize);
            gridTile.setFill(tile.getFill()); // Copy the block's color
            gridTile.setStroke(tile.getStroke()); // Copy the block's stroke

            // Add the new rectangle to the grid at the correct position
            GridPane.setColumnIndex(gridTile, gridCol);
            GridPane.setRowIndex(gridTile, gridRow);
            gameBoard.getGameBoard().add(gridTile, gridCol, gridRow);

            // Mark the tile as occupied
            System.out.println("Block attached at grid position: " + gridCol + ", " + gridRow);
            gameBoard.setTileOccupied(gridRow, gridCol);
        }

        // Check and clear filled lines after placing the block
        gameBoard.checkAndClearFilledLines();
    }

    private static void spawnNewBlocks(Pane gameLayout, GameBoard gameBoard) {
        for (int i = 0; i < 3; i++) {
            Block newBlock = Block.createRandomBlock();
            Pane blockPane = newBlock.getBlockPane();
            double startX = 50 + (i * 200); // Starting X position for blocks
            double startY = gameBoard.getGameBoard().getBoundsInParent().getMaxY() + 20; // Starting Y position for blocks below the grid
            blockPane.setLayoutX(startX);
            blockPane.setLayoutY(startY);
            makeDraggable(blockPane, gameBoard, gameLayout);
            gameLayout.getChildren().add(blockPane);
        }
    }
}