package uet.arkanoid.Menu.HighScore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.arkanoid.PlaySound;
import uet.arkanoid.SaveGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// import javax.swing.Action;

public class HighscoreController {

    @FXML
    private ListView<String> highscoreList;

    // private ArrayList<PlayerData> players = new ArrayList<>();

    // // Lớp dữ liệu nhỏ gọn
    // public static class PlayerData {
    // public String name;
    // public int score;

    // public PlayerData(String name, int score) {
    // this.name = name;
    // this.score = score;
    // }
    // }

    // Hàm này sẽ được gọi từ MenuController khi chuyển sang scene HighScore
    // public void setPlayerData(ArrayList<PlayerData> players) {
    // this.players = players;

    // // Đưa dữ liệu vào ListView
    // ObservableList<String> items = FXCollections.observableArrayList();
    // for (int i = 0; i < players.size(); i++) {
    // PlayerData p = players.get(i);
    // items.add(String.format("%2d. %-15s %6d", i + 1, p.name, p.score));
    // }
    // highscoreList.setItems(items);
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

            System.out.println("Return menu from highscore");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load menu");
        }
    }

    // Dùng để test nhanh nếu chạy riêng scene HighScore
    @FXML
    public void initialize() {
        // if (players.isEmpty()) {
        // players.add(new PlayerData("Player1", 2000));
        // players.add(new PlayerData("Player2", 1500));
        // players.add(new PlayerData("Player3", 1000));
        // setPlayerData(players);
        // }
        List<Pair<String, Integer>> players = SaveGame.loadRanking();
        // Đưa dữ liệu vào ListView
        ObservableList<String> items = FXCollections.observableArrayList();
        // Thêm tiêu đề bảng
        items.add(String.format("%-20s %-50s %8s", "STT", "Tên người chơi", "Điểm"));
        items.add("-----------------------------------------------------------------------------------");

        // Thêm dữ liệu người chơi
        for (int i = 0; i < players.size(); i++) {
            Pair<String, Integer> p = players.get(i);
            items.add(String.format("%-20d %-50s %8d", i + 1, p.getKey(), p.getValue()));
        }
        highscoreList.setItems(items);
        // Căn chỉnh thẳng hàng dữ liệu
        highscoreList.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14;");
    }
}
