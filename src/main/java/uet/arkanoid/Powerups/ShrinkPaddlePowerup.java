package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import uet.arkanoid.Paddle;

public class ShrinkPaddlePowerup extends Powerup {
    private static final Image IMAGE = new Image(
            ShrinkPaddlePowerup.class.getResource("/assets/image/powerups/shrink_paddle.png").toExternalForm());

    private Paddle paddle;

    public ShrinkPaddlePowerup(double x, double y, double width, double height, Paddle paddle, Pane pane) {
        super(x, y, width, height, pane);
        this.paddle = paddle;
    }

    // Constructor cho khi deserialized
    public ShrinkPaddlePowerup(double x, double y, double width, double height, double fallSpeed) {
        super(x, y, width, height, fallSpeed);
        // paddle sẽ được gán lại
    }

    @Override
    public Image loadImage() {
        return IMAGE;
    }

    @Override
    public void active() {
        paddle.shrink();
    }

    // Setter cho paddle khi khôi phục game
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
}