package uet.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestBall extends Application {
    @Override
    public void start(Stage stage) {
        double screenWidth = 600;
        double screenHeight = 400;

        Pane root = new Pane();
        Scene scene = new Scene(root, screenWidth, screenHeight);

        // Tạo bóng
        stage.setTitle("Test Ball");
        stage.setScene(scene);
        stage.show();

        // Vòng lặp update liên tục
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.update();
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
