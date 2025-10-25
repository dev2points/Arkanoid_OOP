package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class HandleInput {
    // Tập hợp lưu các phím đang được nhấn
    private static final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void check_input(Paddle paddle, Ball ball, Scene scene) {
        // Chỉ đăng ký listener 1 lần duy nhất
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
            scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        }

        // Xử lý phím nhấn mỗi frame
        if (pressedKeys.contains(KeyCode.LEFT)) {
            paddle.moveLeft();
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            paddle.moveRight();
        } else {
            paddle.stop();
        }
        if (pressedKeys.contains(KeyCode.S)) {
            ball.slow();
        }
        if (pressedKeys.contains(KeyCode.L)) {
            ball.fast();
        }
        // if (pressedKeys.contains(KeyCode.N)) {
        //     gameController.changeMap();
        //     pressedKeys.remove(KeyCode.N);
        // }

    }
}
