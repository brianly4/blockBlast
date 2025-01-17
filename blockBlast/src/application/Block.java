package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private final Pane blockPane;
    private final int[][] shape;
    private final int tileSize;

    public Block(int[][] shape, int tileSize, Color color) {
        this.shape = shape;
        this.tileSize = tileSize;
        this.blockPane = new Pane();
        drawBlock(color);
    }

    private void drawBlock(Color color) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    Rectangle rect = new Rectangle(tileSize, tileSize);
                    rect.setFill(color);
                    rect.setStroke(Color.BLACK);
                    rect.setX(col * tileSize);
                    rect.setY(row * tileSize);
                    blockPane.getChildren().add(rect);
                }
            }
        }
    }

    public Pane getBlockPane() {
        return blockPane;
    }

    public static List<Block> createBlocks() {
        List<Block> blocks = new ArrayList<>();

        int[][] squareShape = {
            {1, 1},
            {1, 1}
        };

        int[][] lShape = {
            {1, 0, 0},
            {1, 1, 1}
        };

        int[][] tShape = {
            {0, 1, 0},
            {1, 1, 1}
        };

        blocks.add(new Block(squareShape, 40, Color.BLUE));
        blocks.add(new Block(lShape, 40, Color.RED));
        blocks.add(new Block(tShape, 40, Color.GREEN));

        return blocks;
    }
}
