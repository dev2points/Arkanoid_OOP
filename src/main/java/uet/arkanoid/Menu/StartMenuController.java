package uet.arkanoid.Menu;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uet.arkanoid.Gameconfig;
import uet.arkanoid.MultiplayerController;
import uet.arkanoid.PlaySound;

public class StartMenuController extends BaseController{

    @FXML
    private Pane newGameButton;
    @FXML
    private Pane loadGameButton;
    @FXML
    private Pane optionsButton;
    @FXML
    private Pane highScoreButton;

    @FXML
    private void initialize() {
             PlaySound.soundBackground("/assets/sound/backgroundSound.mp3");
    }

    @FXML
    private void handleNewGame(MouseEvent event) {
        menumanager.playNewGame();
    }

    @FXML 
    private void handleNewGame2(MouseEvent event) {
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
            PlaySound.soundBackground("/assets/sound/backgroundSound.mp3");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoadGame(MouseEvent event) {
        menumanager.displayLoadGame();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        menumanager.displayOptionMenu();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        menumanager.displayHighScore();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

}
