package uet.arkanoid.Menu;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class Victory2Controller {
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

    // private GameController gamecontroller;
    // private int score;
    @FXML
    private Text winner_text;

    public void setWinner(String winner) {
        winner_text.setText(winner);
    }

    @FXML
    private void initialize() {
        // Optional hover effects or setup code
        System.out.println("MultiPlayerVictory menu loaded successfully!");
        // gamecontroller.setIsplaying(false);
    }

    @FXML
    private void playagain(MouseEvent event) {
        
    }

    @FXML
    private void returnHome(MouseEvent event) {
        
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        
    }

    // @FXML
    // private void save_score() {
    // String playerName = player_name.getText().trim();
    // // gamecontroller.getUser().setName(playerName);
    // SaveGame.saveScore(playerName, score);
    // System.out
    // .println("Saved highscore with Name: " + playerName + " Score: " + score);
    // if (playerName != "") {
    // victory_menu.getChildren().remove(input_pane);
    // }
    // }

    // public void setGameController(GameController g) {
    // gamecontroller = g;
    // }

    // public void setScore(int score) {
    // player_score.setText("Your Score: " + score);
    // this.score = score;
    // }

}
