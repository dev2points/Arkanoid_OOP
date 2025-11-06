package uet.arkanoid.Menu.StartMenu;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uet.arkanoid.GameController;
import uet.arkanoid.Menu.HighScore.HighscoreController;
import javafx.scene.Node;

public class MenuController {

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
        try {
            // Tải file giao diện load_game.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/LoadGame/load_game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Lấy stage hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Hiển thị giao diện Load Game
            stage.setScene(scene);
            stage.show();

            System.out.println("Load Game scene loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load load_game.fxml");
        }
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        System.out.println("Options clicked!");
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        try {
            // Tải file giao diện highscore.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/HighScore/high_score.fxml"));
            Parent root = loader.load();

            // Lấy controller của Highscore
            HighscoreController controller = loader.getController();

            // // Giả lập danh sách người chơi để test
            // ArrayList<HighscoreController.PlayerData> players = new ArrayList<>();
            // players.add(new HighscoreController.PlayerData("Nhat", 5200));
            // players.add(new HighscoreController.PlayerData("Lan", 4700));
            // players.add(new HighscoreController.PlayerData("Hieu", 8900));
            // players.add(new HighscoreController.PlayerData("Tuan", 3000));

            // // Gửi dữ liệu qua controller
            // controller.setPlayerData(players);

            // Lấy stage hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Hiển thị giao diện Highscore
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Highscore scene loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load highscore.fxml");
        }
    }

}
