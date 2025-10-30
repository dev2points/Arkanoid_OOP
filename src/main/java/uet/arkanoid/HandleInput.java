package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HandleInput {
    private static final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void check_input(Paddle paddle, BallManager ballManager, Scene scene, GameController gamecontroller){
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
            scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        }

        // Di chuyển paddle
        if (pressedKeys.contains(KeyCode.LEFT)) {
            paddle.moveLeft();
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            paddle.moveRight();
        } else {
            paddle.stop();
        }

        // Nhấn SPACE để bắn bóng
        if (pressedKeys.contains(KeyCode.SPACE)) {
            ballManager.launchBalls();
            pressedKeys.remove(KeyCode.SPACE);
        }

        if(pressedKeys.contains(KeyCode.ESCAPE)) {
            pressedKeys.remove(KeyCode.ESCAPE);
            gamecontroller.PauseGame();
        }
    }
}
