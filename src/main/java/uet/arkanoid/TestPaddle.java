package uet.arkanoid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class TestPaddle extends Application {

    @Override
    public void start(Stage stage) {
        // Kích thước cửa sổ
        double screenWidth = 800;
        double screenHeight = 600;

        Pane pane = new Pane();
        Scene scene = new Scene(pane, screenWidth, screenHeight);

        // Tạo paddle ở giữa màn hình
        Paddle paddle = new Paddle(
            screenWidth / 2 - Gameconfig.width_paddle / 2,
            screenHeight - 50,
            pane,
            screenWidth
        );

        // Lắng nghe phím nhấn
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.moveLeft();
                case RIGHT -> paddle.moveRight();
            }
        });

        // Lắng nghe phím nhả
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT, RIGHT -> paddle.stop();
            }
        });

        // Game loop đơn giản để update liên tục
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle.update();
            }
        }.start();

        stage.setScene(scene);
        stage.setTitle("Test Paddle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
