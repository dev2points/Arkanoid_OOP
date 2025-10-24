package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Paddle;

public class ShrinkPaddlePowerup extends Powerup {
    private Paddle paddle;

    public ShrinkPaddlePowerup(double x, double y, double width, double height, Paddle paddle) {
        super(x, y, width, height);
        this.paddle = paddle;

    }

    @Override
    // Load image để sử dụng loadpowerup
    public Image loadImage() {
        return new Image(getClass().getResource("/assets/image/powerups/shrink_paddle.png").toExternalForm());
    }

    @Override
    public void active() {
        paddle.shrink();
    }

}
