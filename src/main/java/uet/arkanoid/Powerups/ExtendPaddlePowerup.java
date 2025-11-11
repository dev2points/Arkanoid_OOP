package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uet.arkanoid.Paddle;

public class ExtendPaddlePowerup extends Powerup {
    private Paddle paddle;

    // Load image một lần duy nhất cho tất cả object
    private static final Image IMAGE = new Image(
            ExtendPaddlePowerup.class.getResource("/assets/image/powerups/extend_paddle.png").toExternalForm());

    public ExtendPaddlePowerup(double x, double y, double width, double height, Paddle paddle, Pane pane) {
        super(x, y, width, height, pane);
        this.paddle = paddle;
    }

    // Constructor cho khi deserialized
    public ExtendPaddlePowerup(double x, double y, double width, double height, double fallSpeed) {
        super(x, y, width, height, fallSpeed);
        // paddle sẽ được gán lại qua GameMap.setPaddle() hoặc
        // GameController.restoreView()
    }

    @Override
    public Image loadImage() {
        return IMAGE; // chỉ trả về ảnh đã load
    }

    @Override
    public void active() {
        paddle.extend();
    }

    // Setter cho paddle khi khôi phục game
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
}