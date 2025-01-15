package block.blast;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        // Load the image
        Image picture2 = new Image ("file:images/playButton.JPG", 200, 0, true, true);
    	Image picture = new Image("file:images/blockblasthome.png", 200, 0, true, true);
        
    	ImageView pictureViewer = new ImageView(picture);
    	ImageView pictureViewer2 = new ImageView(picture2);
        
        // Create the button
        Button menuClick = new Button();
        menuClick.setGraphic(pictureViewer2); 

        
        Stop[] stops = {
            new Stop(0, Color.rgb(23, 72, 183)),
            new Stop(0.5, Color.rgb(20, 124, 211))
        };
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

        
        VBox layout = new VBox(400);  
        layout.setAlignment(Pos.TOP_CENTER);  
        layout.getChildren().addAll(pictureViewer, menuClick);  
        layout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #1748b7, #147cd3);");

        // Create the scene
        Scene scene = new Scene(layout, 600, 700);
        
        // Set up the stage
        stage.setScene(scene);
        stage.show();
       
        stage.setResizable(false); // locks screen
    }

    
    
    public static void main(String[] args) {
        launch(args);
    }
}