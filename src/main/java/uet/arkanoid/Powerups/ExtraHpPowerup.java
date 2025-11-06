package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.GameController;

public class ExtraHpPowerup extends Powerup {
    private transient GameController controller;

    public ExtraHpPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height);
        this.controller = controller;
    }

    @Override
    public Image loadImage() {
        return new Image(getClass().getResource("/assets/image/powerups/hp.png").toExternalForm());
    }

    @Override
    public void active() {
        if (controller != null && controller.getUser() != null) {
            controller.getUser().addHp(1);
        }
    }
}
