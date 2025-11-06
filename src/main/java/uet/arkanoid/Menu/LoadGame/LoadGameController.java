package uet.arkanoid.Menu.LoadGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
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
    private ListView<String> playerList;

    private List<PlayerData> players = new ArrayList<>();

    @FXML
    public void initialize() {
        // Giả lập dữ liệu lưu người chơi
        players.add(new PlayerData("Alice", 1200));
        players.add(new PlayerData("Bob", 950));
        players.add(new PlayerData("Charlie", 1800));

        // Đưa dữ liệu vào ListView
        for (PlayerData p : players) {
            playerList.getItems().add(p.getName() + " - Score: " + p.getScore());
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
    }

    // Lớp tạm lưu dữ liệu người chơi
    public static class PlayerData {
        private String name;
        private int score;

        public PlayerData(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() { return name; }
        public int getScore() { return score; }
    }
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Quay lại menu chính
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/menu/menu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            System.out.println("Returned to main menu!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load menu.fxml");
        }
    }

}
