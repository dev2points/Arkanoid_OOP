package uet.arkanoid;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
public class GameController {
    private long lastTime = 0;
    @FXML
    private Pane root;
    @FXML
    private Label score;

    @FXML
    public void initialize() {
        LevelLoader(1);
        // score.setText("Score: 1000000");
        // setupInput();
        // Mainloop();
    }
    
    private void LevelLoader(int level) {
        Paddle paddle = new Paddle(root);
        
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
    }

    private void PauseGame() {

    }

    private void ContinueGame() {

    }

    private void BacktoMain() {

    }

    
}



