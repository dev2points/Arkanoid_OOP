package uet.arkanoid;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
public class GameController {
    private long lastTime = 0;
    @FXML
    private Pane root;
    private Ball ball;
    private List<Brick> bricks = new ArrayList<>();
    private Paddle paddle;

    @FXML
    private Label score;

    @FXML
    public void initialize() {
        LevelLoader(1);
        // score.setText("Score: 1000000");
        // setupInput();
        // Mainloop();
        
        showBrick();
        showBall();
        showPaddle();

        Platform.runLater(this::setupInput);

        startGameLoop();
    }

    private void setupInput() {
    // Lấy scene từ root
    root.getScene().setOnKeyPressed(event -> {
        switch (event.getCode()) {
            case LEFT -> paddle.moveLeft();
            case RIGHT -> paddle.moveRight();
            default -> {}
        }
    });

    root.getScene().setOnKeyReleased(event -> {
        switch (event.getCode()) {
            case LEFT, RIGHT -> paddle.stop();
            default -> {}
        }
    });

        Platform.runLater(() -> root.requestFocus());

}


    private void showBrick() {
        bricks.clear(); 

}

    

        private void showBall() {
            Platform.runLater(() -> {
                double w = root.getWidth();
                double h = root.getHeight();
                ball = new Ball(300, 400, root, w, h);
            });
        }


    private void showPaddle() {
         paddle = new Paddle(700,700,root,1280);
    }
    private void LevelLoader(int level) {
        //paddle = new Paddle(root);
        
        score.setText("Score: 1000000");

        
        // Viet logic spawn gi day vao day
        
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }


                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                update(deltaTime);
            }
        };
        gameLoop.start();
    }   

    private void update(double dt) {
        // loop, update, timer gi day viet vao day
         ball.update();
         //brick.update();
         paddle.update();
         Collision.checkPaddleCollision(ball, paddle);
         Collision.checkBrickCollision(ball, bricks);
    }

    private void PauseGame() {

    }

    private void ContinueGame() {

    }

    private void BacktoMain() {

    }

    
}



