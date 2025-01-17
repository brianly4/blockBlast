package application;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DragUtils {

    public static void makeDraggable(Pane blockPane) {
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
}
