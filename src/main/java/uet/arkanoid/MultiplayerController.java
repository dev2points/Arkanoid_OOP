package uet.arkanoid;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uet.arkanoid.Menu.Victory2Controller;

public class MultiplayerController {
    private GameController player1Controller;
    private GameController player2Controller;
    private Pane rootPane;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;

    public MultiplayerController(Pane pane, Scene scene) {
        this.rootPane = pane;

        // Hai vùng chơi riêng
        Pane player1Pane = new Pane();
        Pane player2Pane = new Pane();

        player1Pane.setPrefSize(Gameconfig.screen_width, Gameconfig.screen_height);
        player2Pane.setPrefSize(Gameconfig.screen_width, Gameconfig.screen_height);
        player1Pane.setLayoutX(0);
        player2Pane.setLayoutX(Gameconfig.screen_width);

        rootPane.getChildren().addAll(player1Pane, player2Pane);

        player1Controller = new GameController(1, player1Pane, scene);
        player2Controller = new GameController(2, player2Pane, scene);

        MainLoop(scene);
    }

    private void MainLoop(Scene scene) {
        System.out.println("start game");
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkResult();
            }
        };
        gameLoop.start();
    }

    private void checkResult() {
        boolean player1Win = player1Controller.IsVictory() || player2Controller.IsLoose();
        boolean player2Win = player2Controller.IsVictory() || player1Controller.IsLoose();

        if (player1Win || player2Win) {
            gameLoop.stop();
            player1Controller.PauseGame();
            player2Controller.PauseGame();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/assets/menu/fxml/victory_menu_2.fxml"));
                Parent root1 = loader.load();
                Victory2Controller victory = loader.getController();

                if (player1Win && player2Win)
                    victory.setWinner("DRAW");
                else if (player1Win)
                    victory.setWinner("🏆 Player 1 Wins!");
                else
                    victory.setWinner("🏆 Player 2 Wins!");

                Scene scene = new Scene(root1);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Multiplayer Victory Menu");
                stage.show();
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading multiplayer victory menu");
            }
        }
    }

}
