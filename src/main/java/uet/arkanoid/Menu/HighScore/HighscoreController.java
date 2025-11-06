package uet.arkanoid.Menu.HighScore;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class HighscoreController {

    @FXML
    private ListView<String> highscoreList;

    private ArrayList<PlayerData> players = new ArrayList<>();

    // Lớp dữ liệu nhỏ gọn
    public static class PlayerData {
        public String name;
        public int score;

        public PlayerData(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    // Hàm này sẽ được gọi từ MenuController khi chuyển sang scene HighScore
    public void setPlayerData(ArrayList<PlayerData> players) {
        this.players = players;

        // Sắp xếp giảm dần theo điểm
        players.sort((a, b) -> Integer.compare(b.score, a.score));

        // Đưa dữ liệu vào ListView
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < players.size(); i++) {
            PlayerData p = players.get(i);
            items.add(String.format("%2d. %-15s %6d", i + 1, p.name, p.score));
        }
        highscoreList.setItems(items);
    }

    @FXML
    private void handleBack() {
        System.out.println("Back to main menu...");
        // TODO: Chuyển scene về menu chính (sau này thêm code)
    }

    // Dùng để test nhanh nếu chạy riêng scene HighScore
    @FXML
    public void initialize() {
        if (players.isEmpty()) {
            players.add(new PlayerData("Player1", 2000));
            players.add(new PlayerData("Player2", 1500));
            players.add(new PlayerData("Player3", 1000));
            setPlayerData(players);
        }
    }
}
