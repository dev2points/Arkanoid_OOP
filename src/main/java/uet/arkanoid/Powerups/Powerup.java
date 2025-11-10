package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uet.arkanoid.BaseObject;
import uet.arkanoid.GameController;
import uet.arkanoid.Gameconfig;

public abstract class Powerup extends BaseObject {
    private double fallSpeed = Gameconfig.speed_powerup;
    public static transient GameController controller;

    public Powerup(double x, double y, double width, double height, Pane pane) {
        super(x, y, width, height, pane);
        loadPowerup(loadImage());
    }

    public static void setGameController(GameController gameController) {
        controller = gameController;
    }

    // Load image để sử dụng loadpowerup
    public abstract Image loadImage();

    public void loadPowerup(Image image) {
        // Tạo ImageView và thêm vào pane
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        iv.setLayoutX(x);
        iv.setLayoutY(y);

        setView(iv);
        pane.getChildren().add(iv);
    }

    public abstract void active();

    // Cập nhật vị trí của powerup.
    public void update() {
        y += fallSpeed * deltatime;
        if (view instanceof ImageView img) {
            img.setLayoutY(y);
        }
    }
}
