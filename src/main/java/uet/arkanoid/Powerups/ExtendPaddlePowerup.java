package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Paddle;

public class ExtendPaddlePowerup extends Powerup {
    private Paddle paddle;

    public ExtendPaddlePowerup(double x, double y, double width, double height, Paddle paddle) {
        super(x, y, width, height);
        this.paddle = paddle;

    }

    @Override
    // Load image để sử dụng loadpowerup
    public Image loadImage() {
        return new Image(getClass().getResource("/assets/image/powerups/extend_paddle.png").toExternalForm());
    }

    @Override
    public void active() {
        paddle.extend();
    }

}
