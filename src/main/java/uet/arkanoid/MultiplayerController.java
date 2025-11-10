package uet.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
        player1Pane.setLayoutX((Gameconfig.screen_width * 2 - (2 *
                Gameconfig.screen_width)) / 2);
        player2Pane.setLayoutX(player1Pane.getLayoutX() + Gameconfig.screen_width);

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
        if (player1Controller.IsVictory() || player2Controller.IsVictory()) {
            gameLoop.stop();
            if (player1Controller.IsVictory()) {
                showResult("Player 1 wins!");
            } else if (player2Controller.IsVictory()) {
                showResult("Player 2 wins!");
            } else {
                showResult("Draw!");
            }
        }
    }

    private void showResult(String msg) {
        Label label = new Label(msg);
        label.setStyle("-fx-font-size: 36px; -fx-text-fill: yellow;");
        label.setLayoutX(rootPane.getWidth() / 2 - 100);
        label.setLayoutY(rootPane.getHeight() / 2 - 50);
        rootPane.getChildren().add(label);
    }
}
