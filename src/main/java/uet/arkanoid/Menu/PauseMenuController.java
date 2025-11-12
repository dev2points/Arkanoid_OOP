package uet.arkanoid.Menu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import uet.arkanoid.GameController;
import uet.arkanoid.PlaySound;
import uet.arkanoid.SaveGame;

public class PauseMenuController extends BaseController{
    @FXML
    private TextField player_name;
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
    private GameController gamecontroller;

    @FXML
    private void initialize() {
        // Optional hover effects or setup code
        System.out.println("Menu loaded successfully!");
        GameController.setIsplaying(false);
    }

    @FXML
    private void continueGame(MouseEvent event) {
        menumanager.popMenu();
        gamecontroller.ContinueGame();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void returnHome(MouseEvent event) {
        menumanager.clearMenus();
        menumanager.displayStartMenu();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        menumanager.displayOptionMenu();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void saveAndExit(MouseEvent event) {
        input_pane.setVisible(true);
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void save_game(MouseEvent event) {
        String name = player_name.getText().trim();
        if (name.trim().isEmpty()) {
            System.out.println("No name entered, game not saved.");
        } else {
            gamecontroller.getUser().setName(name);
            SaveGame.saveGame(gamecontroller);
            SaveGame.saveScore(name, gamecontroller.getUser().getScore());
            System.out.println("Game saved for user: " + name);
            returnHome(event);
        }
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");

    }

    public void setGameController(GameController g) {
        gamecontroller = g;
    }

}
