package uet.arkanoid.Menu.LoadGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.arkanoid.GameController;
import uet.arkanoid.GameMap;
import uet.arkanoid.PlaySound;
import uet.arkanoid.SaveGame;
import uet.arkanoid.User;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadGameController {

    @FXML
    private ListView<String> playerList = new ListView<>();

    private List<Pair<GameMap, User>> players = new ArrayList<>();

    @FXML
    public void initialize() {
        players = SaveGame.loadGame();
        if (players == null)
            System.out.println("loadGame return null");
        else
            // Đưa dữ liệu vào ListView
            for (Pair<GameMap, User> p : players) {
                playerList.getItems().add(p.getValue().getName() + " - Score: " + p.getValue().getScore());
            }
    }

    @FXML
    private void handleLoad(ActionEvent event) {
        int selectedIndex = playerList.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a player first!");
            alert.showAndWait();
            return;
        }

        System.out.println("Selected index: " + selectedIndex);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/maps/main.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            GameController controller = loader.getController();
            controller.loadProcess(players.get(selectedIndex));
            controller.setScene(scene);

            SaveGame.processChoosed(selectedIndex);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            PlaySound.soundEffect("/assets/sound/clickSound.mp3");

            System.out.println("Process saved loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load game from process");
        }
    }

    // Lớp tạm lưu dữ liệu người chơi
    // public static class PlayerData {
    // private String name;
    // private int score;

    // public PlayerData(String name, int score) {
    // this.name = name;
    // this.score = score;
    // }

    // public String getName() {
    // return name;
    // }

    // public int getScore() {
    // return score;
    // }
    // }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/StartMenu/menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            PlaySound.soundEffect("/assets/sound/clickSound.mp3");

            System.out.println("Return menu from loadGame!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load menu.fxml");
        }
    }

}
