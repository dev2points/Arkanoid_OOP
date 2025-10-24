package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.arkanoid.BaseObject;
import uet.arkanoid.Gameconfig;

public abstract class Powerup extends BaseObject {
    private double fallSpeed = Gameconfig.speed_powerup;

    public Powerup(double x, double y, double width, double height) {
        super(x, y, width, height);
        loadPowerup(loadImage());
    }

    // Load image để sử dụng loadpowerup
    public abstract Image loadImage();

    public void loadPowerup(Image image) {
        // Tạo ImageView và thêm vào pane
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        System.out.println(x);
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
