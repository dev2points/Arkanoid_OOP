package uet.arkanoid.Menu;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.arkanoid.Gameconfig;
import uet.arkanoid.MultiplayerController;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        try {
            Pane root = new Pane();
            Scene scene = new Scene(root, Gameconfig.screen_width * 2,
                    Gameconfig.screen_height);

            MultiplayerController multiplayerController = new MultiplayerController(root,
                    scene);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnHome(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/menu/fxml/stack_pane.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Test Paddl11e");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
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
