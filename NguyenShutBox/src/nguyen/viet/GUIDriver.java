package nguyen.viet;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *   Shut The Box game   
 * 
 * Try to flip all the tiles! A higher score is worse than
 * a lower one.  
 * 
 * @author V. Nguyen  
 * @date December 30, 2024  
 */
public class GUIDriver extends Application {
	Die d1 = new Die();
	boolean isRolled = false;
	int sumPicked = 0;
	int roundNum = 0;
	int score = 0;

	@Override
	public void start(Stage stage) throws Exception {
		VBox vbox = new VBox(10);

		// Create hbox and vbox's
		Label title = new Label("Shut The Box");
		HBox tileBox = new HBox(10);
		HBox roundBox = new HBox(20);
		HBox totalScore = new HBox(20);
		HBox controlBlocks = new HBox(20);
		HBox rollBlocks = new HBox(20);
		Button[] tileBtns = new Button[9];
		Tile[] tiles = new Tile[9];

		// tile button blocks
		for (int i = 0; i < tileBtns.length; i++) {
			tileBtns[i] = new Button(String.valueOf(i + 1));
			tileBtns[i].setStyle("-fx-background-color:#EEEEEE;");
			tiles[i] = new Tile(i + 1);
			tileBox.getChildren().add(tileBtns[i]);
		}
		tileBox.setAlignment(Pos.CENTER);

		// All buttons and vbox's
		Button btnRoll = new Button("ROLL DICE");
		Button btnRoll2 = new Button("ROLL DIE");
		Button lockIn = new Button("LOCK IN?");
		Button endRound = new Button("END ROUND?");
		Label roundScore = new Label("ROUND");
		Label scoreLabel = new Label(String.valueOf(score));
		Label theScore = new Label("SCORE");
		Label roundLabel = new Label(String.valueOf(roundNum));
		Label result = new Label("Result");
		Label lblValue = new Label(); // output of results
		controlBlocks.getChildren().addAll(lblValue);
		totalScore.getChildren().addAll(theScore, scoreLabel);
		roundBox.getChildren().addAll(roundScore, roundLabel);
		rollBlocks.setAlignment(Pos.TOP_RIGHT);
		rollBlocks.getChildren().addAll(btnRoll, btnRoll2, endRound);
		rollBlocks.setAlignment(Pos.CENTER);
		controlBlocks.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(roundBox, totalScore, title, tileBox, rollBlocks, result, controlBlocks, lockIn);
		vbox.setAlignment(Pos.CENTER);

		// initially set button as disabled
		btnRoll2.setDisable(true);

		// roll dice actions
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
		// roll die actions
		btnRoll2.setOnAction(e -> {
			int faceValue = d1.roll();
			if (isRolled == false) {
				lblValue.setText(String.valueOf(faceValue));
				btnRoll.setDisable(true);
				btnRoll2.setDisable(true);
				isRolled = true;
			}

		});

		// tile button actions
		for (Button b : tileBtns) {
			b.setOnAction(e -> {
				// if buttons gray, set to green
				if (b.getStyle().equals("-fx-background-color:#EEEEEE;") && isRolled) {
					b.setStyle("-fx-background-color: green;");
					sumPicked = sumPicked + Integer.valueOf(b.getText());

				} else {
					// sets color to gray when clicked again
					if (b.getStyle().equals("-fx-background-color: green;") && isRolled) {
						b.setStyle("-fx-background-color:#EEEEEE;");

						sumPicked = sumPicked - Integer.valueOf(b.getText());

					}

				}

			});
		}

		// lock's result
		lockIn.setOnAction(e -> {
			if (Integer.valueOf(lblValue.getText()) == sumPicked) {
				for (Button b : tileBtns) {
					if ((b.getStyle().equals("-fx-background-color: green;"))) {
						b.setStyle("-fx-background-color: black;");
						b.setDisable(true);
						btnRoll.setDisable(false);
						isRolled = false;
						sumPicked = 0;
						lblValue.setText(null);
					}
				}

				// if 789 down, enable roll die
				if (tileBtns[6].getStyle().equals(("-fx-background-color: black;"))) {
					if (tileBtns[7].getStyle().equals(("-fx-background-color: black;"))) {
						if (tileBtns[8].getStyle().equals(("-fx-background-color: black;"))) {
							btnRoll2.setDisable(false);
						}
					}
				}
			}

		});

		// end round button
		endRound.setOnAction(e -> {
			// increases round number
			roundNum = roundNum + 1;

			// Add any remaining values from unlocked tiles to score before disabling
			if (roundNum < 6) {
				for (Button b : tileBtns) {
					if ((b.getStyle() == ("-fx-background-color: green;"))) {
						b.setStyle("-fx-background-color:#EEEEEE;");
					}
					if (b.getStyle().equals("-fx-background-color:#EEEEEE;")) {
						score += Integer.valueOf(b.getText());
					}

				}
			}

			// disables everything after 5 rounds
			if (roundNum == 5) {
				lockIn.setDisable(true);
				btnRoll.setDisable(true);
				btnRoll2.setDisable(true);
				roundLabel.setText(String.valueOf(roundNum));
				scoreLabel.setText(String.valueOf(score));
				theScore.setText("FINAL SCORE: ");
				endRound.setDisable(true);
				for (Button b : tileBtns) {
					b.setDisable(true);
					b.setStyle(("-fx-background-color:#EEEEEE;")); // sets color back to normal
				}
			}
			// restarts the game
			if (roundNum < 5) {
				sumPicked = 0;
				isRolled = false;
				lblValue.setText(null);
				btnRoll.setDisable(false); // Re-enable the roll button for the next round
				for (Button b : tileBtns) {
					if ((b.getStyle() != ("-fx-background-color: black;"))) {
						b.setStyle("-fx-background-color:#EEEEEE;");
						b.setDisable(false);
					} else if ((b.getStyle().equals("-fx-background-color: black;"))) {
						b.setStyle("-fx-background-color:#EEEEEE;");
						b.setDisable(false);
					}
				}
			}

			// update score labels once round ends
			roundLabel.setText(String.valueOf(roundNum));
			scoreLabel.setText(String.valueOf(score));
		});
		Scene scene = new Scene(vbox, 500, 400);
		stage.setScene(scene);

		stage.show();
	}

	// launches javafx
	public static void main(String[] args) {
		launch(args);
	}
}
