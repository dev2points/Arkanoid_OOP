package uet.arkanoid.Menu.OptionMenu;

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

public class OptionController {
    private GameController gamecontroller;
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
        // Optional hover effects or setup code
        System.out.println("Menu loaded successfully!");
    }

    @FXML
    private void handleNewGame(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/maps/main.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            GameController controller = loader.getController();
            controller.setScene(scene); 

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
    private void handleLoadGame(MouseEvent event) {
        System.out.println("Load Game clicked!");
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        System.out.println("Options clicked!");
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        System.out.println("High Score clicked!");
    }
}
