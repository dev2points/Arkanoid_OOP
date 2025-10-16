package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
public class GameController {

     @FXML
    private Pane root;
    @FXML
    private Label score;

    private Paddle paddle;
    private Ball ball;
    private long lastTime;
    private Scene scene;

    @FXML
    public void initialize() {
        LevelLoader (1);
        MainLoop();
      
    
    }
    
    
    private void LevelLoader(int level) {
        paddle = new Paddle(root);
        ball =new Ball(root);
        // score.setText("Score: 1000000");
        // Viet logic spawn gi day vao day
        
    }    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    private void MainLoop() {
        // loop, update, timer gi day viet vao day
         Timer time = new Timer();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                time.update();
                if (scene != null) {
                    HandleInput.check_input(paddle, ball, scene);
                }
                paddle.update(time.getDeltaTime());
                ball.update(time.getDeltaTime());
                Collision.checkPaddleCollision(ball, paddle);

            }
        }.start();
    }
   

    private void PauseGame() {

    }

    private void ContinueGame() {

    }

    private void BacktoMain() {

    }

    
}



