package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class HandleInputMultiplayer {
    private static final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void checkInput(
            Scene scene,
            GameController player1Controller,
            GameController player2Controller) {
        // Gắn listener nếu chưa có
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
            scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        }

        // Player 1
        Paddle paddle1 = player1Controller.getPaddle();
        BallManager ball1 = player1Controller.getBallManager();

        if (pressedKeys.contains(KeyCode.A)) {
            paddle1.moveLeft();
        } else if (pressedKeys.contains(KeyCode.D)) {
            paddle1.moveRight();
        } else {
            paddle1.stop();
        }

        if (pressedKeys.contains(KeyCode.W)) {
            ball1.launchBalls();
            pressedKeys.remove(KeyCode.W);
        }

        // Player 2
        Paddle paddle2 = player2Controller.getPaddle();
        BallManager ball2 = player2Controller.getBallManager();

        if (pressedKeys.contains(KeyCode.LEFT)) {
            paddle2.moveLeft();
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            paddle2.moveRight();
        } else {
            paddle2.stop();
        }

        if (pressedKeys.contains(KeyCode.UP)) {
            ball2.launchBalls();
            pressedKeys.remove(KeyCode.UP);
        }

        // --- ESC: tạm dừng cả 2 ---
        if (pressedKeys.contains(KeyCode.ESCAPE)) {
            pressedKeys.remove(KeyCode.ESCAPE);
            player1Controller.PauseGame();
            player2Controller.PauseGame();
        }
    }
}
