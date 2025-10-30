package uet.arkanoid.Menu.PauseMenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uet.arkanoid.GameController;
import javafx.scene.Node;

public class PauseController {

    @FXML
    private Pane continueButton;
    @FXML
    private Pane returnButton;
    @FXML
    private Pane optionsButton;
    @FXML
    private Pane highScoreButton;

    private GameController gamecontroller;
    @FXML
    private void initialize() {
        // Optional hover effects or setup code
        System.out.println("Menu loaded successfully!");
    }

    @FXML
    private void continueGame(MouseEvent event) {
        gamecontroller.ContinueGame();
    }

    @FXML
    private void returnHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/StartMenu/menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

            System.out.println("New Game scene loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load game.fxml");
        }
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        System.out.println("Options clicked!");
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        System.out.println("High Score clicked!");
    }

    public void setGameController(GameController g){
        gamecontroller = g;
    }

}
