package uet.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Kích thước cửa sổ
        double screenWidth = Gameconfig.screen_width;
        double screenHeight = Gameconfig.screen_height;

        Pane pane = new Pane();
        Scene scene = new Scene(pane, screenWidth, screenHeight);
        List<Brick> bricks = new ArrayList<>();
        Brick brick = new Brick(Gameconfig.screen_width / 2, 100, pane, 3);
        bricks.add(brick);
        Ball ball = new Ball(Gameconfig.screen_width / 2, 2, pane, screenWidth, screenHeight);

        // Tạo paddle ở giữa màn hình
        Paddle paddle = new Paddle(
                screenWidth / 2 - Gameconfig.width_paddle / 2,
                screenHeight - 50,
                pane,
                screenWidth);

        // Game loop đơn giản để update liên tục
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.update();
                Collision.checkPaddleCollision(ball, paddle);
                Collision.checkBrickCollision(ball, bricks);
                HandleInput.check_input(paddle, ball, scene);
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
