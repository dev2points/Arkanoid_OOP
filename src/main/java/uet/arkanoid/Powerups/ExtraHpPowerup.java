package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.GameController;

public class ExtraHpPowerup extends Powerup {

    // Ảnh cache chỉ load một lần duy nhất
    private static final Image IMAGE = new Image(
        ExtraHpPowerup.class.getResource("/assets/image/powerups/hp.png").toExternalForm()
    );

    private GameController controller;

    public ExtraHpPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height);
        this.controller = controller;
    }

    @Override
    public Image loadImage() {
        return IMAGE; // dùng ảnh đã cache
    }

    @Override
    public void active() {
        if (controller != null && controller.getUser() != null) {
            controller.getUser().addHp(1);
        }
    }
}
