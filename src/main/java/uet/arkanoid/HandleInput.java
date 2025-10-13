package uet.arkanoid;

import javafx.scene.Scene;

public class HandleInput {
    public static void check_input(Paddle paddle, Ball ball, Scene scene) {
        // Lắng nghe phím nhấn
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.moveLeft();
                case RIGHT -> paddle.moveRight();
                case UP -> paddle.extend();
                case DOWN -> paddle.shrink();
                case S -> ball.slow();
                case L -> ball.fast();
            }
        });

        // Lắng nghe phím nhả
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT, RIGHT -> paddle.stop();
            }
        });
    }
}
