package uet.arkanoid.Menu.VictoryMenu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.arkanoid.GameController;
import uet.arkanoid.Menu.HighScore.HighscoreController;
import javafx.scene.Node;
import javafx.scene.control.TextField;
public class VictoryController {
    @FXML
    private Text player_score;
    @FXML
    private Pane victory_menu;
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
    @FXML
    private TextField player_name;

    private GameController gamecontroller;
    @FXML
    private void initialize() {
        // Optional hover effects or setup code
        System.out.println("Menu loaded successfully!");
    }

    @FXML
    private void playagain(MouseEvent event) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/OptionMenu/option_menu.fxml"));
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
    

    @FXML
    private void save_score(){
        String playerName = player_name.getText().trim();
        System.out.println(playerName);
        if (playerName != "") {
            victory_menu.getChildren().remove(input_pane);
        }
    }

    public void setGameController(GameController g){
        gamecontroller = g;
    }
    
    public void setScore(int score) {
        player_score.setText("Your Score: " + score);
    }

}
