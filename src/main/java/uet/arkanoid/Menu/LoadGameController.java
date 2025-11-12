package uet.arkanoid.Menu;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;
import uet.arkanoid.GameMap;
import uet.arkanoid.PlaySound;
import uet.arkanoid.SaveGame;
import uet.arkanoid.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class LoadGameController extends BaseController {

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
        menumanager.loadSavedGame(players.get(selectedIndex));
        SaveGame.processChoosed(selectedIndex);
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
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
        menumanager.popMenu();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }

}
