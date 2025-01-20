package block.blast;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static Block createRandomBlock() {
        Random random = new Random();
        int tileSize = 40;

        // All possible block shapes
        int[][][] shapes = {
            // Square blocks
            {
                {1}  
            },
            
            {
                {1, 1},  
                {1, 1}
            },
            {
                {1, 1, 1},  
                {1, 1, 1},
                {1, 1, 1}
            },
            // Line blocks
            {
                {1, 1}  
            },
            {
                {1},  
                {1}
            },
            {
            	{1, 1, 1} 
            },
            {
            	{1}, 
            	{1},
            	{1}	
            },
            {
            	{1, 1, 1, 1} 
            }, 
            {
            	{1}, 
            	{1},
            	{1},
            	{1}
            },
            {
            	{1, 1, 1, 1, 1} 
            },
            {
            	{1}, 
            	{1},
            	{1},
            	{1},
            	{1}
            },
            // Z-shapes
            {
            	{0, 1, 1}, 
            	{1, 1, 0}
            },
            {
            	{1, 1, 0},
            	{0, 1, 1}
            },
            {
            	{0, 1},
            	{1, 1},
            	{1, 0}
            },
            {
            	{1, 0},
            	{1, 1},
            	{0, 1}
            },
            // L-shapes
            {
            	{0, 1},
            	{1, 1},
            },
            {
            	{1, 0},
            	{1, 1},
            },
            {
            	{1, 1},
            	{0, 1},
            },
            {
            	{1, 1},
            	{1, 0},
            },
            {
            	{1, 0},
            	{1, 0},
            	{1, 1}
            },
            {
            	{0, 1},
            	{0, 1},
            	{1, 1}
            },
            {
            	{1, 1},
            	{0, 1},
            	{0, 1}
            },
            {
            	{1, 1},
            	{1, 0},
            	{1, 0}
            },
            {
            	{1, 1, 1},
            	{0, 0, 1}
            },
            {
            	{1, 1, 1},
            	{1, 0, 0}
            },
            {
            	{1, 0, 0},
            	{1, 1, 1}
            },
            {
            	{0, 0, 1},
            	{1, 1, 1}
            },
            {
                {1, 0, 0},  
                {1, 0, 0},
                {1, 1, 1}
            },
            {
                {1, 1, 1},  
                {0, 0, 1},
                {0, 0, 1}
            },
            {
                {0, 0, 1},  
                {0, 0, 1},
                {1, 1, 1}
            },
            {
                {1, 1, 1},  
                {1, 0, 0},
                {1, 0, 0}
            },
        };

        // Colors for the blocks
        Color[] colors = {
            Color.BLUE, 
            Color.RED, 
            Color.GREEN, 
            Color.YELLOW, 
            Color.PURPLE, 
            Color.ORANGE, 
            Color.CYAN, 
            Color.PINK, 
            Color.BROWN
        };

        // Randomly select a shape and color
        int[][] shape = shapes[random.nextInt(shapes.length)];
        Color color = colors[random.nextInt(colors.length)];

        return new Block(shape, tileSize, color);
    }
}