package uet.arkanoid.Menu;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import uet.arkanoid.GameController;
import uet.arkanoid.SaveGame;

public class VictoryController extends BaseController{
    @FXML
    private Text player_score;
    @FXML
    private Pane victory_menu;
    @FXML
    private Pane continueButton;
    @FXML
    private Pane returnButton;
    @FXML
    private Pane optionsButton;
    @FXML
    private Pane highScoreButton;
    @FXML
    private Pane input_pane;
    @FXML
    private TextField player_name;

    private int score;

    @FXML
    private void initialize() {
        // Optional hover effects or setup code
        System.out.println("Victory menu loaded successfully!");
        GameController.setIsplaying(false);
    }

    @FXML
    private void playagain(MouseEvent event) {
        menumanager.playNewGame();
    }

    @FXML
    private void returnHome(MouseEvent event) {
        menumanager.displayStartMenu();
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        menumanager.displayOptionMenu();
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        menumanager.displayHighScore();
    }

    @FXML
    private void save_score() {
        String playerName = player_name.getText().trim();
        // gamecontroller.getUser().setName(playerName);
        SaveGame.saveScore(playerName, score);
        System.out
                .println("Saved highscore with Name: " + playerName + " Score: " + score);
        if (playerName != "") {
            victory_menu.getChildren().remove(input_pane);
        }
    }


    public void setScore(int score) {
        player_score.setText("Your Score: " + score);
        this.score = score;
    }

}
