package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.GameController;

public class ExtraHpPowerup extends Powerup {

    // Ảnh cache chỉ load một lần duy nhất
    private static final Image IMAGE = new Image(
            ExtraHpPowerup.class.getResource("/assets/image/powerups/hp.png").toExternalForm());

    private transient GameController controller; // Transient

    public ExtraHpPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height, controller.getPane());
        this.controller = controller;
    }

    // Constructor cho khi deserialized
    public ExtraHpPowerup(double x, double y, double width, double height, double fallSpeed) {
        super(x, y, width, height, fallSpeed);
        // controller sẽ được gán lại sau
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

    // // Setter cho controller khi khôi phục game
    // public void setGameController(GameController controller) {
    // this.controller = controller;
    // }
}