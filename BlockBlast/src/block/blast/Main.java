package block.blast;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load the images
        Image picture2 = new Image("file:images/playButton.JPG", 200, 0, true, true);
        Image picture = new Image("file:images/blockblasthome.png", 200, 0, true, true);

        ImageView pictureViewer = new ImageView(picture);
        ImageView pictureViewer2 = new ImageView(picture2);

        // Set the images and button to be centered horizontally
        pictureViewer.setTranslateY(100); // Move the image 50px down

        // Create the button
        Button menuClick = new Button();
        menuClick.setGraphic(pictureViewer2);
        menuClick.setTranslateY(420); // Set the Y position of the play button

        // Use Pane for absolute positioning
        Pane layout = new Pane();
        layout.getChildren().addAll(pictureViewer, menuClick);

        // Set the background gradient
        layout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #1748b7, #147cd3);");

        // Create the scene
        Scene scene = new Scene(layout, 600, 700);

        // Center the blockblasthome image horizontally
        pictureViewer.setTranslateX(190);

        // Center the play button horizontally
        menuClick.setTranslateX(180);

        // Set up the stage
        stage.setScene(scene);
        stage.show();

        stage.setResizable(false); // Lock screen size
    }

    public static void main(String[] args) {
        launch(args);
    }
}