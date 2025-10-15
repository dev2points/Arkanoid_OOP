package uet.arkanoid;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
public class GameController {

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
        
        // score.setText("Score: 1000000");
        // Viet logic spawn gi day vao day
        
    }
    
    private void MainLoop() {
        // loop, update, timer gi day viet vao day
    }

    private void PauseGame() {

    }

    private void ContinueGame() {

    }

    private void BacktoMain() {

    }

    
}



