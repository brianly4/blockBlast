package block.blast;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class DragUtils {

    private static double startX;
    private static double startY;

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

            if (isWithinBounds(x, y, gameBoard.getGameBoard())) {
                if (canPlaceBlock(blockPane, gameBoard)) {
                    attachBlockToBoard(blockPane, gameBoard);
                    gameLayout.getChildren().remove(blockPane);
                    spawnNewBlock(gameLayout, gameBoard);
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

            if (gridRow < 0 || gridRow >= 10 || gridCol < 0 || gridCol >= 10
                    || gameBoard.isTileOccupied(gridRow, gridCol)) {
                return false;
            }
        }

        return true;
    }

    private static void attachBlockToBoard(Pane blockPane, GameBoard gameBoard) {
        double x = blockPane.getLayoutX();
        double y = blockPane.getLayoutY();
        double tileSize = gameBoard.getTileSize();

        int col = (int) ((x - gameBoard.getGameBoard().getLayoutX()) / tileSize);
        int row = (int) ((y - gameBoard.getGameBoard().getLayoutY()) / tileSize);

        // Check if the block overlaps with any occupied tile
        for (int i = 0; i < blockPane.getChildren().size(); i++) {
            double childX = blockPane.getChildren().get(i).getLayoutX() + col * tileSize;
            double childY = blockPane.getChildren().get(i).getLayoutY() + row * tileSize;

            int gridCol = (int) (childX / tileSize);
            int gridRow = (int) (childY / tileSize);

            if (gameBoard.isTileOccupied(gridRow, gridCol)) {
                System.out.println("Block placement failed: Tile is already occupied.");
                blockPane.setLayoutX(startX);
                blockPane.setLayoutY(startY);
                return;
            }
        }

        // Mark the occupied tiles
        for (int i = 0; i < blockPane.getChildren().size(); i++) {
            double childX = blockPane.getChildren().get(i).getLayoutX() + col * tileSize;
            double childY = blockPane.getChildren().get(i).getLayoutY() + row * tileSize;

            int gridCol = (int) (childX / tileSize);
            int gridRow = (int) (childY / tileSize);

            gameBoard.setTileOccupied(gridRow, gridCol);
        }

        // Set the layout position of the block to fit within the grid cell
        blockPane.setLayoutX(col * tileSize);
        blockPane.setLayoutY(row * tileSize);

        // Add the block to the grid pane at the calculated column and row
        gameBoard.getGameBoard().add(blockPane, col, row);

        // Debugging output
        System.out.println("Block attached at grid position: " + col + ", " + row);
    }


    private static void spawnNewBlock(Pane gameLayout, GameBoard gameBoard) {
        Block newBlock = Block.createRandomBlock();
        Pane blockPane = newBlock.getBlockPane();
        double startX = 50;
        double startY = gameBoard.getGameBoard().getBoundsInParent().getMaxY() + 20;
        blockPane.setLayoutX(startX);
        blockPane.setLayoutY(startY);
        makeDraggable(blockPane, gameBoard, gameLayout);
        gameLayout.getChildren().add(blockPane);
    }
}
