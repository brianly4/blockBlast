package nguyen.viet;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIDriver extends Application {
	Die d1 = new Die();
	boolean isRolled = false;
	boolean SEN = false;
	int sumPicked = 0;
	int roundNum = 0;
	int score = 0;
	@Override
	public void start(Stage stage) throws Exception {
		VBox vbox = new VBox(10);

		// Create and display the title
		Label title = new Label("Shut The Box");
		vbox.getChildren().add(title);

		HBox tileBox = new HBox(10);
		HBox roundBox = new HBox(20);
		HBox totalScore = new HBox(20);
		HBox controlBlocks = new HBox(20);
		HBox rollBlocks = new HBox(20);
		Button[] tileBtns = new Button[9];
		Tile[] tiles = new Tile[9];

		for (int i = 0; i < tileBtns.length; i++) {
			tileBtns[i] = new Button(String.valueOf(i + 1));
			tileBtns[i].setStyle("-fx-background-color:#EEEEEE;");
			tiles[i] = new Tile(i + 1);
			tileBox.getChildren().add(tileBtns[i]);
		}
		tileBox.setAlignment(Pos.CENTER);
		

		Button btnRoll = new Button("ROLL DICE");
		Button btnRoll2 = new Button("ROLL DIE");
		Button lockIn = new Button("LOCK IN?");
		Button endRound = new Button("END ROUND?");
		Label roundScore = new Label("ROUND");
		Label scoreLabel = new Label("");
		Label theScore = new Label("SCORE");
		Label roundLabel = new Label("");
		Label result = new Label("Result");
		Label lblValue = new Label(); // output of results
		controlBlocks.getChildren().addAll(lblValue);
		totalScore.getChildren().addAll(theScore, scoreLabel);
		roundBox.getChildren().addAll(roundScore, roundLabel);
		rollBlocks.setAlignment(Pos.TOP_RIGHT);
		rollBlocks.getChildren().addAll(btnRoll, btnRoll2, endRound);
		rollBlocks.setAlignment(Pos.CENTER);
		controlBlocks.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(roundBox, totalScore, tileBox, rollBlocks, result, controlBlocks, lockIn);

		vbox.setAlignment(Pos.CENTER);

		if (SEN == false) {
			btnRoll2.setDisable(true);
		} else if (SEN) {
			btnRoll2.setDisable(false);
			;
		}

		btnRoll.setOnAction(e -> {
			int faceValue = d1.roll();
			int faceValue2 = d1.roll();

			if (isRolled == false) {
				lblValue.setText(String.valueOf(faceValue + faceValue2));
				isRolled = true;
				btnRoll.setDisable(true);
				btnRoll2.setDisable(true);
			}

		});

		btnRoll2.setOnAction(e -> {
			int faceValue = d1.roll();
			if (isRolled == false && SEN) {
				lblValue.setText(String.valueOf(faceValue));
				btnRoll.setDisable(true);
				btnRoll2.setDisable(true);
			}

		});

		for (Button b : tileBtns) {
			b.setOnAction(e -> {
				if (b.getStyle().equals("-fx-background-color:#EEEEEE;") && isRolled) {
					b.setStyle("-fx-background-color: green;");
					sumPicked = sumPicked + Integer.valueOf(b.getText());
					System.out.println(sumPicked);

				} else {
					if (b.getStyle().equals("-fx-background-color: green;") && isRolled) {
						b.setStyle("-fx-background-color:#EEEEEE;");
						sumPicked = sumPicked - Integer.valueOf(b.getText());
						System.out.println(sumPicked);
					}

				}

			});
		}

		lockIn.setOnAction(e -> {
			if (Integer.valueOf(lblValue.getText()) == sumPicked) {
				System.out.println("Hello");
				for (Button b : tileBtns) {
					if ((b.getStyle().equals("-fx-background-color: green;"))) {
						b.setStyle("-fx-background-color: black;");
						b.setDisable(true);
						btnRoll.setDisable(false);
						isRolled = false;
						System.out.println("Ok");
						sumPicked = 0;
						lblValue.setText(null);
					}
				}
			}

		});

		endRound.setOnAction(e -> {
			roundNum = roundNum + 1;

			if (roundNum == 5) {
				lockIn.setDisable(true);
				btnRoll.setDisable(true);
				btnRoll2.setDisable(true);

			} else if (roundNum < 5) {
				sumPicked = 0;
				isRolled = false;
				lblValue.setText(null);
				btnRoll.setDisable(false);
				for (Button b : tileBtns) {
					if ((b.getStyle().equals("-fx-background-color: black;"))) {
						b.setStyle("-fx-background-color:#EEEEEE;");
						b.setDisable(false);
					}
					if (b.getStyle().equals(("-fx-background-color: green;"))) {
						b.setStyle("-fx-background-color:#EEEEEE;");
						b.setDisable(false);
						score = score +  Integer.valueOf(b.getText());
					}
				}
			}

		});
		Scene scene = new Scene(vbox, 500, 400);
		stage.setScene(scene);

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
